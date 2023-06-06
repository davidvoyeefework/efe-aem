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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

/**
 * The Class InboxNotificationSenderImpl.
 */
@Component(service = WorkflowProcess.class, immediate = true, property = {"process.label=" + "Inbox notification sender"})
public class InboxNotificationSenderImpl implements WorkflowProcess {

    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(InboxNotificationSenderImpl.class);

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
        ResourceResolver resourceResolver = session.adaptTo(ResourceResolver.class);
        if (Objects.nonNull(resourceResolver)) {
            TaskManager taskManager = resourceResolver.adaptTo(TaskManager.class);
            createTask(taskManager, item);
        }
    }

    /**
     * createTask
     * @param taskManager
     * @param item
     */
    private void createTask(TaskManager taskManager, WorkItem item) {
        if(Objects.nonNull(taskManager)) {
            Workflow workflow = item.getWorkflow();
            String participant = workflow.getInitiator();
            WorkflowData workflowData = item.getWorkflowData();
            String payloadPath = workflowData.getPayload().toString();
            String title = workflowData.getMetaDataMap().get("workflowTitle", String.class);
            String comment = item.getMetaDataMap().get("comment", String.class);
            try {
                Task newTask = taskManager.getTaskManagerFactory().newTask(NOTIFICATION_TASK_TYPE);
                newTask.setName(title);
                newTask.setCurrentAssignee(participant);
                newTask.setDescription(comment);
                newTask.setContentPath(payloadPath);
                taskManager.createTask(newTask);
            } catch (TaskManagerException e) {
                log.error("TaskManagerException occurred", e);
            }
        }
    }
}
