package com.efe.core.workflow;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.apache.sling.api.resource.ResourceResolver;
import com.efe.core.services.LocationModelServices;
import com.efe.core.services.PlannerModelServices;
import com.efe.core.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Reference;

/**
 * The Class LocationPlannerManualUpdateImpl.
 */
@Component(service = WorkflowProcess.class, immediate = true, property = {"process.label=" + "Inbox notification sender"})
public class LocationPlannerManualUpdateImpl implements WorkflowProcess {
    /**
     * PlannerModelServices injected
     */
    @Reference
    private PlannerModelServices plannerModelServices;
            /**
     * LocationModelServices injected
     */
    @Reference
    private LocationModelServices locationModelServices;
    
    /**
    * ResourceResolverFactory injected
    */
   @Reference
   private transient ResourceResolverFactory resourceResolverFactory;

    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(LocationPlannerManualUpdateImpl.class);

    /** The Constant NOTIFICATION_TASK_TYPE. */
    public static final String NOTIFICATION_TASK_TYPE = "Notification";

    /**
     * execute
     * @param item
     * @param session
     * @param args
     */
    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        try (ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resourceResolverFactory)) {
            plannerModelServices.addDataToCFModelPlanner(resourceResolver);
            locationModelServices.addDataToCFModelLocation(resourceResolver);
        }
    }

}
