package com.efe.core.schedulers;

import java.util.Collection;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.event.jobs.JobBuilder;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.event.jobs.ScheduledJobInfo;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PlannerScheduler.
 *
 */
@Component(name = "Planner and Location Scheduler", immediate = true)
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
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerLocationJob.class);

	@Activate
	@Modified
	protected void activate(final PlannerLocationJob.PlannerConfiguration config) {
		if (StringUtils.equalsIgnoreCase(config.serviceEnabled(), BooleanUtils.TRUE)) {
			removeScheduler();
			startScheduledJob(config);
			LOGGER.debug("PrintTemplateEventSyncJob Scheduler Activated ");
		} else {
			removeScheduler();
			LOGGER.debug("PrintTemplateEventSyncJob is not enabled.");
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
			LOGGER.error("Error adding scheduledJobInfo");
		} else {
			LOGGER.debug("Scheduler Job added to the Queue {} with cron {}");
		}

	}

	/**
	 * PlannerConfiguration Interface.
	 *
	 */
	@ObjectClassDefinition(name = "Planner and Location Scheduler Configuration")
	public @interface PlannerConfiguration {
		/**
		 * schedulerExpression
		 * 
		 * @return schedulerExpression
		 */
		@AttributeDefinition(name = "Corn Expression", description = "Corn Expression default: once a day", type = AttributeType.STRING)
		public String schedulerExpression() default "0 0/10 * 1/1 * ? *";

		/**
		 * serviceEnabled
		 * 
		 * @return serviceEnabled
		 */
		@AttributeDefinition(name = "Enabled", description = "Enable Scheduler", type = AttributeType.BOOLEAN)
		public String serviceEnabled() default "true";

	}
}
