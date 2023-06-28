package com.efe.core.workflow;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.taskmanagement.TaskManagerFactory;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The Class InboxNotificationSenderImplTest.
 */
@ExtendWith(MockitoExtension.class)
class InboxNotificationSenderImplTest {

	/** The InboxNotificationSenderImpl. */
	private InboxNotificationSenderImpl inboxNotificationSender = new InboxNotificationSenderImpl();

	/** The workItem. */
	@Mock
	private WorkItem workItem;

	/** The workflowSession. */
	@Mock
	private WorkflowSession workflowSession;

	/** The metaDataMap. */
	@Mock
	private MetaDataMap metaDataMap;

	/** The resourceResolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The taskManager. */
	@Mock
	private TaskManager taskManager;

	@BeforeEach
	void setup() {
		when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(taskManager);
		Workflow workflow = mock(Workflow.class);
		when(workItem.getWorkflow()).thenReturn(workflow);
		String participant = "initiator";
		when(workflow.getInitiator()).thenReturn(participant);
		WorkflowData workflowData = mock(WorkflowData.class);
		when(workItem.getWorkflowData()).thenReturn(workflowData);
		when(workflowData.getPayload()).thenReturn("/content/efe/test.html");
		MetaDataMap metaDataMap1 = mock(MetaDataMap.class);
		when(workflowData.getMetaDataMap()).thenReturn(metaDataMap1);
		when(metaDataMap1.get("workflowTitle", String.class)).thenReturn("site publish");
		when(workItem.getMetaDataMap()).thenReturn(metaDataMap1);
		when(metaDataMap1.get("comment", String.class)).thenReturn("Rejected by reviewer");
	}

	/**
	 * Test execute.
	 */
	@Test
	void executeTest() throws WorkflowException, TaskManagerException {
		TaskManagerFactory taskManagerFactory = mock(TaskManagerFactory.class);
		when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);
		Task newTask = mock(Task.class);
		when(taskManagerFactory.newTask("Notification")).thenReturn(newTask);
		inboxNotificationSender.execute(workItem, workflowSession, metaDataMap);
		assertNotNull(newTask);
	}

	/**
	 * Test execute exception.
	 */
	@Test
	void executeTestException() throws WorkflowException, TaskManagerException {
		TaskManagerFactory taskManagerFactory = mock(TaskManagerFactory.class);
		when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);
		doThrow(TaskManagerException.class).when(taskManagerFactory).newTask("Notification");
		inboxNotificationSender.execute(workItem, workflowSession, metaDataMap);
		assertEquals("initiator", workItem.getWorkflow().getInitiator());
	}
}
