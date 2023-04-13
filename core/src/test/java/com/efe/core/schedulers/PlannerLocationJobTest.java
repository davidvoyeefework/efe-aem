package com.efe.core.schedulers;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.sling.event.jobs.JobBuilder;
import org.apache.sling.event.jobs.JobBuilder.ScheduleBuilder;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.event.jobs.ScheduledJobInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Class PlannerLocationJobTest
 *
 */
@ExtendWith(MockitoExtension.class)
class PlannerLocationJobTest {

	/**
	 * Job Topic
	 */
	private static final String JOB_TOPIC = "PlannerLocationJobTopic";

	/**
	 * Injects PlannerLocationJob
	 */
	@InjectMocks
	private PlannerLocationJob plannerLocationJob;

	/**
	 * JobManager
	 */
	@Mock
	private JobManager jobManager;

	/**
	 * ScheduledJobInfo
	 */
	@Mock
	private ScheduledJobInfo jobInfo;

	/**
	 * PlannerConfiguration
	 */
	@Mock
	private PlannerLocationJob.PlannerConfiguration config;

	/**
	 * JobBuilder
	 */
	@Mock
	private JobBuilder builder;

	/**
	 * ScheduleBuilder
	 */
	@Mock
	private ScheduleBuilder scheduleBuilder;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSuccess() {

		lenient().when(config.schedulerExpression()).thenReturn("0 0/2 * 1/1 * ? *");
		lenient().when(config.serviceEnabled()).thenReturn("true");

		List<ScheduledJobInfo> scheduledJobs = new ArrayList<>();
		scheduledJobs.add(jobInfo);

		lenient().when(jobManager.getScheduledJobs(JOB_TOPIC, 0, (Map<String, Object>[]) null))
				.thenReturn(scheduledJobs);

		lenient().when(jobManager.createJob(anyString())).thenReturn(builder);

		lenient().when(builder.properties(anyMap())).thenReturn(builder);

		lenient().when(builder.schedule()).thenReturn(scheduleBuilder);

		lenient().when(scheduleBuilder.add()).thenReturn(jobInfo);

		plannerLocationJob.activate(config);

		Mockito.verify(scheduleBuilder, Mockito.times(1)).add();

	}

	@Test
	void testSchedulerDisabled() {

		lenient().when(config.schedulerExpression()).thenReturn("false");

		List<ScheduledJobInfo> scheduledJobs = new ArrayList<>();

		lenient().when(jobManager.getScheduledJobs(JOB_TOPIC, 0, (Map<String, Object>[]) null))
				.thenReturn(scheduledJobs);

		plannerLocationJob.activate(config);

		Mockito.verify(scheduleBuilder, Mockito.times(0)).add();

	}

	@Test
	void testSchedulerFailed() {

		lenient().when(config.schedulerExpression()).thenReturn("0 0/2 * 1/1 * ? *");
		lenient().when(config.serviceEnabled()).thenReturn("true");

		List<ScheduledJobInfo> scheduledJobs = new ArrayList<>();
		scheduledJobs.add(jobInfo);

		lenient().when(jobManager.getScheduledJobs(JOB_TOPIC, 0, (Map<String, Object>[]) null))
				.thenReturn(scheduledJobs);

		lenient().when(jobManager.createJob(anyString())).thenReturn(builder);

		lenient().when(builder.properties(anyMap())).thenReturn(builder);

		lenient().when(builder.schedule()).thenReturn(scheduleBuilder);

		lenient().when(scheduleBuilder.add()).thenReturn(null);

		plannerLocationJob.activate(config);

		Mockito.verify(scheduleBuilder, Mockito.times(1)).add();

	}

}
