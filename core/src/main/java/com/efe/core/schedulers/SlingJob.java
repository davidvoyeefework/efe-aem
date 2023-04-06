package com.efe.core.schedulers;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.services.LocationModelServices;
import com.efe.core.services.PlannerModelServices;

/**
 * The SlingJob class is a job consumer that is triggered by a job topic
 * "midnight/job/topic".
 *
 */
@Component(service = JobConsumer.class, immediate = true, property = {
		JobConsumer.PROPERTY_TOPICS + "=daily/job/topic" })
public class SlingJob implements JobConsumer {

	/**
	 * PlannerModelServices injected
	 */
	@Reference
	PlannerModelServices plannerModelServices;

	/**
	 * LocationModelServices injected
	 */
	@Reference
	LocationModelServices locationModelServices;

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SlingJob.class);

	/**
	 * The process() method is called when a job is triggered. It logs a message and
	 * then executes the two methods. If an exception occurs during the execution of
	 * the methods, the method returns a JobResult of FAILED.
	 */
	@Override
	public JobResult process(Job job) {
		try {
			LOGGER.error("Sling JOB Called");
			plannerModelServices.addDataToCFModelPlanner();
			locationModelServices.addDataToCFModelLocation();
			return JobConsumer.JobResult.OK;
		} catch (Exception exception) {
			LOGGER.error("Exception ", exception);
			return JobResult.FAILED;
		}
	}
}