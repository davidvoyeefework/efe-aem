package com.efe.core.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.io.IOException;


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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

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
import com.google.gson.JsonObject;
import org.osgi.service.component.annotations.Reference;

/**
 * The Class InboxNotificationSenderImpl.
 */
@Component(service = WorkflowProcess.class, immediate = true, property = {"process.label=" + "EFE Print Post Service"})
public class PostToWebServiceProcessStep implements WorkflowProcess {

	 private static final String PARAM_SEPARATOR = ",";
	 
	 private static final String ARG_API_URL = "apiurl";
	 
	 private static final String ARG_TARGET = "target";
/**
    * ResourceResolverFactory injected
    */
   @Reference
   private transient ResourceResolverFactory resourceResolverFactory;
	 
    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(PostToWebServiceProcessStep.class);

    /**
     * execute
     * @param item
     * @param session
     * @param args
     */
    @Override
    
    public void execute(WorkItem workItem, WorkflowSession session, MetaDataMap processArguments) throws WorkflowException {
       
        Map<String, String> processStepArguments = parseProcessStepArguments(processArguments);
        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        Map<String, Object> jMap = new HashMap();
        jMap.put(ARG_TARGET, processStepArguments.get(ARG_TARGET));
        jMap.put("Path", payloadPath);
        jMap.put("initiatedBy",workItem.getWorkflow().getInitiator());
            
        try (ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resourceResolverFactory)) {
            ContentFragment thisFrag = resourceResolver.resolve(payloadPath).adaptTo(ContentFragment.class);
            FragmentTemplate thisTemplate = thisFrag.getTemplate();
            jMap.put("name", thisFrag.getName());
            jMap.put("description", thisFrag.getDescription());
            jMap.put("tags", thisFrag.getTags());
            jMap.put("title", thisFrag.getTitle());
            
            
            
            Iterator<ContentElement> contentIterator = thisFrag.getElements();
            Map<String,Map> fragmentContent = new HashMap();
            while(contentIterator.hasNext()) {
                ContentElement thisElement = contentIterator.next();
                FragmentData thisElemData = thisElement.getValue();
                ElementTemplate thisElemTemplate = thisTemplate.getForElement(thisElement);
                Map<String, String> elementProperties = new HashMap();
                elementProperties.put("contentValue", thisElement.getContent());
                elementProperties.put("contentDataType", thisElement.getContentType());
                elementProperties.put( "contentTypeString", thisElemData.getDataType().getTypeString());
                elementProperties.put("contentDefault", thisElemTemplate.getDefaultContent());
                
                fragmentContent.put(thisElement.getName(), elementProperties);
            }
            jMap.put("content", fragmentContent);
           
        } catch (Exception e) {
            jMap.put("Content",e.getMessage());
        }
        
        if(processStepArguments.containsKey(ARG_API_URL) && processStepArguments.containsKey(ARG_TARGET)) {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String jsonOut = "";
            try {
                jsonOut = objMapper.writeValueAsString(jMap);
            } catch (Exception e) {
                jsonOut = e.getMessage();
            }
            
            // Create an instance of CloseableHttpClient
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                
                // Create an instance of HttpPost with the API endpoint URL
                HttpPost httpPost = new HttpPost(processStepArguments.get(ARG_API_URL));

                // Set the request headers, if needed
                httpPost.setHeader("Content-Type", "application/json");

                // Set the request body with the JSON data
                StringEntity requestEntity = new StringEntity(jsonOut);
                httpPost.setEntity(requestEntity);

                // Execute the request and get the response
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    // Process the response
                    int statusCode = response.getStatusLine().getStatusCode();  

                    log.info("Status Code: {}", statusCode);
                    // Get the response body, if needed
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        String responseBody = EntityUtils.toString(responseEntity);
                        log.info("Response Body: {}", responseBody);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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

}


