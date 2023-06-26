package com.efe.core.schedulers;

import java.util.Collection;

import org.apache.sling.event.jobs.JobBuilder;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.event.jobs.ScheduledJobInfo;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PlannerScheduler.
 *
 */
@Component
@Designate(ocd = PlannerLocationJob.PlannerConfiguration.class)
public class PlannerLocationJob {

	/**
	 * PlannerScheduler TOPIC
	 */
	private static final String TOPIC = "daily/job/topic";

	/**
	 * JobManager injected
	 */
	@Reference
	private JobManager jobManager;
	
	/**
	 * PlannerConfiguration Interface.
	 *
	 */
	@ObjectClassDefinition(name = "EFE Planner and Location Scheduler Configuration", description = "Planner and Location Scheduler Configuration")
	public static @interface PlannerConfiguration {

		/**
		 * schedulerExpression
		 * 
		 * @return schedulerExpression
		 */
		@AttributeDefinition(name = "Corn Expression", description = "Corn Expression default: once a day")
		String schedulerExpression();

		/**
		 * enableSchedular
		 * 
		 * @return serviceEnabled
		 */
		@AttributeDefinition(name = "Enable Scheduler?", description = "Enable Scheduler")
		boolean enableSchedular();
	}

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerLocationJob.class);

	@Activate
	@Modified
	protected void activate(final PlannerLocationJob.PlannerConfiguration config) {
		LOGGER.debug("PlannerLocationJob.activate method called {} {}", config.schedulerExpression(), config.enableSchedular());
		if (config.enableSchedular()) {
			removeScheduler();
			startScheduledJob(config);
			LOGGER.info("PlannerLocationJob Scheduler Activated ");
		} else {
			removeScheduler();
			LOGGER.info("PlannerLocationJob is not enabled.");
		}
	}

	/**
	 * Remove a scheduler based on the job topic
	 */
	private void removeScheduler() {
		Collection<ScheduledJobInfo> scheduledJobInfos = jobManager.getScheduledJobs(TOPIC, 0, null);
		for (ScheduledJobInfo scheduledJobInfo : scheduledJobInfos) {
			LOGGER.debug("Unscheduling Job {}", scheduledJobInfo.getJobProperties());
			scheduledJobInfo.unschedule();
		}
	}

	/**
	 * Start a scheduler based on the job topic
	 */
	private void startScheduledJob(final PlannerLocationJob.PlannerConfiguration config) {

		JobBuilder.ScheduleBuilder scheduleBuilder = jobManager.createJob(TOPIC).schedule();

		scheduleBuilder.cron(config.schedulerExpression());
		ScheduledJobInfo scheduledJobInfo = scheduleBuilder.add();

		if (scheduledJobInfo == null) {
			LOGGER.info("Error adding scheduledJobInfo");
		} else {
			LOGGER.info("Scheduler Job added to the Queue with cron : {}", config.schedulerExpression());
		}
	}

}
