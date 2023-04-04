package com.efe.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.wcm.core.components.models.Component;
import com.adobe.cq.export.json.ExporterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FAQAccordionModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FAQAccordionModel {

    /**
     * Name of the child node storing the list items.
     */
    String NN_STATIC = "static";

    protected static final String RESOURCE_TYPE = "efe/components/faqaccordion";

    private static final Logger LOG = LoggerFactory.getLogger(FAQAccordionModel.class);

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE)
    @Default(values="No resourceType")
    protected String resourceType;
    
    @ScriptVariable
    private Resource resource;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ScriptVariable
    private Component component;

    private final List<FAQModel> list = new ArrayList<>();

    @PostConstruct
    protected void init(){

        Resource staticNode = resource.getChild(NN_STATIC);

        Node node = staticNode.adaptTo(Node.class);

        try {
            NodeIterator childNodes = node.getNodes();

            if(childNodes != null) {
                while(childNodes.hasNext()) {
                    Node item = childNodes.nextNode();

                    String cf_path = item.getProperty("fragmentPath").getString();

                    Resource fragmentResource = resourceResolver.getResource(cf_path);

                    FAQModel faq = new FAQModel(fragmentResource);

                    list.add(faq);
                }
            }
        } catch (RepositoryException e) {
            LOG.error("Node canot be obtained from repository.",e);
        }

    }

    public List<FAQModel> getList() {
        return list;
    }
}
