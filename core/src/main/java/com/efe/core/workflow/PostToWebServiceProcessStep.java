package com.efe.core.workflow;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.security.PrivateKey;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import com.efe.core.services.EfeService;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import java.security.KeyPair;
import com.adobe.granite.keystore.KeyStoreService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.adobe.cq.dam.cfm.ElementTemplate;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.cq.dam.cfm.FragmentData;
import com.efe.core.utils.ResourceUtil;
import org.osgi.service.component.annotations.Reference;
import static java.nio.charset.StandardCharsets.*;

/**
 * The Class InboxNotificationSenderImpl.
 */
@Component(service = WorkflowProcess.class, immediate = true, property = {"process.label=" + "EFE Print Post Service"})
public class PostToWebServiceProcessStep implements WorkflowProcess {

    private static final String PARAM_SEPARATOR = ",";

    private static final String ARG_API_URL = "apiurl";

    private static final String ARG_TARGET = "target";
    
    @Reference 
    private KeyStoreService keyStoreService; 

    private PrivateKey getPrivateKey(ResourceResolver resourceResolver){ 
        KeyPair keyPair = keyStoreService.getKeyStoreKeyPair(resourceResolver,"efe-service-user", "print_cert"); 
        return keyPair.getPrivate(); 
    }

    /**
     * EfeService injected
     */
    @Reference
    private EfeService efeService;
/**
    * ResourceResolverFactory injected
    */
   @Reference
   private transient ResourceResolverFactory resourceResolverFactory;
	 
    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(PostToWebServiceProcessStep.class);

    /**
     * execute
     * @param workItem
     * @param session
     * @param processArguments
     * @throws WorkflowException
     */
    @Override
    
    public void execute(WorkItem workItem, WorkflowSession session, MetaDataMap processArguments) throws WorkflowException {
        Map<String, String> processStepArguments = parseProcessStepArguments(processArguments);
        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        Map<String, Object> jMap = new HashMap();
        jMap.put(ARG_TARGET, processStepArguments.get(ARG_TARGET));
        jMap.put("Path", payloadPath);
        jMap.put("initiatedBy",workItem.getWorkflow().getInitiator());
        ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resourceResolverFactory);

        FragmentTemplate thisTemplate;
        ContentFragment thisFrag = resourceResolver.resolve(payloadPath).adaptTo(ContentFragment.class);
        if(null != thisFrag) {
            thisTemplate = thisFrag.getTemplate();
            jMap.put("name", thisFrag.getName());
            jMap.put("description", thisFrag.getDescription());
            /*
            try {
                jMap.put("tags", thisFrag.getTags());
            } catch (Exception e) {
                jMap.put("tags", "");
            }
            */
            jMap.put("title", thisFrag.getTitle());
        } else {
            throw new WorkflowException("Content fragment reference is null.");
        }

        Iterator<ContentElement> contentIterator = thisFrag.getElements();
        Map<String,Map> fragmentContent = new HashMap();
        while(contentIterator.hasNext()) {
            ContentElement thisElement = contentIterator.next();
            FragmentData thisElemData = thisElement.getValue();
            ElementTemplate thisElemTemplate = thisTemplate.getForElement(thisElement);
            Map<String, String> elementProperties = new HashMap();
            elementProperties.put("contentValue", thisElement.getContent().replace("=","\\="));
            elementProperties.put("contentDataType", thisElement.getContentType().replace("=","\\="));
            elementProperties.put( "contentTypeString", thisElemData.getDataType().getTypeString().replace("=","\\=;"));
            elementProperties.put("contentDefault", thisElemTemplate.getDefaultContent());

            fragmentContent.put(thisElement.getName(), elementProperties);
        }
        jMap.put("content", fragmentContent);
        
        String accessToken = "";
        if(processStepArguments.containsKey(ARG_API_URL) && processStepArguments.containsKey(ARG_TARGET)) {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String jsonOut = "";
            try {
                jsonOut = objMapper.writeValueAsString(jMap);
            } catch (Exception e) {
                throw new WorkflowException("Failed to parse content fragment to JSON.\r\nException Message: " + e.getMessage());
            }

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(efeService.getPartnerAPIAuthURL().trim() + "?grant_type=" + "urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Ajwt-bearer");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                List<NameValuePair> formParams = new ArrayList();
                formParams.add(new BasicNameValuePair("client_id", efeService.getPrintClientId().trim()));
                formParams.add(new BasicNameValuePair("client_secret",efeService.getPrintClientSecret().trim()));
                formParams.add(new BasicNameValuePair("id_token", getJWTHeader()));
                UrlEncodedFormEntity formValues = new UrlEncodedFormEntity(formParams);
                httpPost.setEntity(formValues);
                // Execute the request and get the response
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    // Process the response
                    int statusCode = response.getStatusLine().getStatusCode();
                    if(statusCode >= 400) {
                        throw new Exception("PartnerAPI auth connection failure, HTTP Status - " + statusCode);
                    }
                    if(statusCode == 200) {
                        HttpEntity responseEntity = response.getEntity();
                        if(responseEntity != null) {
                            // Response should contain access_token and expires_in.
                            String responseString = EntityUtils.toString(responseEntity);
                            Map<String, String> responseMap = objMapper.readValue(responseString, HashMap.class);
                            accessToken = responseMap.get("access_token");
                        }
                    }
                }
            } catch (Exception e) {
                throw new WorkflowException("Error when attempting to contact auth service.\r\nException message: " + e.getMessage());
            }
            
            if(accessToken != "") {
                            // Create an instance of CloseableHttpClient
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

                    // Create an instance of HttpPost with the API endpoint URL
                    HttpPost httpPost = new HttpPost(processStepArguments.get(ARG_API_URL));
                    
                    // Set the request headers, if needed
                    httpPost.setHeader("Authorization", "Bearer " + accessToken);
                    httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");


                    // Set the request body with the JSON data
                    StringEntity requestEntity = new StringEntity(jsonOut, UTF_8);

                    httpPost.setEntity(requestEntity);
                    // Execute the request and get the response
                    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                        
                        // Process the response
                        int statusCode = response.getStatusLine().getStatusCode();  
                        if(statusCode >= 400) {
                            throw new Exception("PrintAPI connection failure, HTTP Status - " + statusCode);
                        }
                        
                        // Get the response body, if needed
                        HttpEntity responseEntity = response.getEntity();
                        if (responseEntity != null) {
                            String responseBody = EntityUtils.toString(responseEntity);
                            log.info("PrintAPI response body: {}", responseBody);
                        }
                    }
                } catch (Exception e) {
                    throw new WorkflowException("Error when attempting to contact print service.\r\nException message: " + e.getMessage());
                }
            } else {
                throw new WorkflowException("No access token generated from PartnerAPI.");
            }
	 
        } else {
            if(!processStepArguments.containsKey(ARG_API_URL)) {
                throw new WorkflowException("API URL missing from process arguments");
            } else {
                throw new WorkflowException("Target missing from process arguments");
            }
            
        }    
    }

    /**
     * split the arguments passed to the ProcessStep into a map
     * 
     * @param args the arguments
     * @return the processed arguments in a map
     */
    protected static Map<String, String> parseProcessStepArguments(MetaDataMap args) {

        Map<String, String> map = new HashMap<>();

        if (args.containsKey("PROCESS_ARGS")) {
            String argumentString = args.get("PROCESS_ARGS", String.class);
            log.trace("extracted argument string {}", argumentString);
            String[] arguments = argumentString.split(PARAM_SEPARATOR);
            for (String argument : arguments) {
                String[] split = argument.split("=");
                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();
                    map.put(key, value);
                } else if (StringUtils.isNotEmpty(argument)) {
                    log.warn("incomplete process step argument. Cannot process argument '{}'; ignoring it", argument);
                }
            }
        }
        return map;
    }
    
    private String getJWTHeader() throws JOSEException {
        // Create the JWT header
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(efeService.getPartnerAPIAuthKID()).build();
        // Create the JWT claims set (payload)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(efeService.getPartnerAPIAuthIssuer())
                .subject(efeService.getPartnerAPIAuthSub())
                .audience(efeService.getPartnerAPIAuthAudience())
                .expirationTime(new Date(System.currentTimeMillis() + 600000)) // Expires in 10 minutes
                .build();

        // Create the signed JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // Sign the JWT using a secret key
        JWSSigner signer = new RSASSASigner(getPrivateKey(ResourceUtil.getServiceResourceResolver(resourceResolverFactory))); // Replace with your actual secret key
        signedJWT.sign(signer);

        // Serialize the JWT to a string
        String jwtToken = signedJWT.serialize();

        return jwtToken;
    }

}


