package com.efe.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class WorkflowParticipantChooserImplTest.
 */
@ExtendWith(MockitoExtension.class)
class WorkflowParticipantChooserImplTest {

	/** The WorkflowParticipantChooserImpl. */
	private WorkflowParticipantChooserImpl initiatorParticipantChooser = new WorkflowParticipantChooserImpl();

	/** The Constant PROCESS_ARGS. */
	private static final String PROCESS_ARGS = "PROCESS_ARGS";

	/** The workItem. */
	@Mock
	private WorkItem workItem;

	/** The workflowSession. */
	@Mock
	private WorkflowSession workflowSession;

	/** The metaDataMap. */
	@Mock
	private MetaDataMap metaDataMap;

	/**
	 * Test get participant approver.
	 */
	@Test
	void getParticipantTestApprover() throws WorkflowException {
		String participant = "EFE AEM Sites Approvers";
		Mockito.when(metaDataMap.get(PROCESS_ARGS, "string")).thenReturn("Sites Approver");
		assertEquals(participant, initiatorParticipantChooser.getParticipant(workItem, workflowSession, metaDataMap));
	}
}
