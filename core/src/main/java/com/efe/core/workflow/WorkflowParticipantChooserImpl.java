package com.efe.core.workflow;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;

/**
 * The Class WorkflowParticipantChooserImpl.
 */
@Component(service = ParticipantStepChooser.class, immediate = true, property = {"chooser.label=" + "Workflow Participant Chooser (service)"})
public class WorkflowParticipantChooserImpl implements ParticipantStepChooser {

    /** The Constant LOGGER. */
    private static final Logger logger = LoggerFactory.getLogger(WorkflowParticipantChooserImpl.class);

    /** The Constant PROCESS_ARGS. */
    private static final String PROCESS_ARGS = "PROCESS_ARGS";

    /**
     * Get participant
     * @param workItem
     * @param workflowSession
     * @param metaDataMap
     * @return participant
     */
    public String getParticipant(WorkItem workItem, WorkflowSession workflowSession,
                                 MetaDataMap metaDataMap) throws WorkflowException {
        String participant = StringUtils.EMPTY;
        String args = metaDataMap.get(PROCESS_ARGS, "string");
        if (args.equalsIgnoreCase("Sites Approver")) {
            participant = "EFE AEM Sites Approvers";
        }
        logger.info("Assigning Dynamic Participant Step work item to {}",participant);
        return participant;
    }
}

