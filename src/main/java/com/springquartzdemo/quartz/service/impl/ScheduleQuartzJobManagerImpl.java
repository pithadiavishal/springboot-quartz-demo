package com.springquartzdemo.quartz.service.impl;

import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.springquartzdemo.quartz.dto.SchedulerJobInfoDTO;
import com.springquartzdemo.quartz.entity.SchedulerJobInfo;
import com.springquartzdemo.quartz.repository.SchedulerRepository;
import com.springquartzdemo.quartz.service.ScheduleQuartzJobManager;
import com.springquartzdemo.quartz.utility.ScheduleQuartzJobUtility;

@Service
public class ScheduleQuartzJobManagerImpl implements ScheduleQuartzJobManager {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private SchedulerRepository schedulerRepository;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ScheduleQuartzJobUtility scheduleCreator;

	public SchedulerMetaData getMetaData() throws SchedulerException {
		SchedulerMetaData metaData = scheduler.getMetaData();
		return metaData;
	}

	public List<SchedulerJobInfo> getAllJobList() {
		return schedulerRepository.findAll();
	}

    public void deleteJob(SchedulerJobInfoDTO jobInfo) {
        try {
        	SchedulerJobInfo getJobInfo = schedulerRepository.findByJobName(jobInfo.getJobName());
        	schedulerRepository.delete(getJobInfo);
        	log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " deleted.");
            schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
        }
    }

    public void pauseJob(SchedulerJobInfoDTO jobInfo) {
        try {
        	SchedulerJobInfo getJobInfo = schedulerRepository.findByJobName(jobInfo.getJobName());
        	getJobInfo.setJobStatus("PAUSED");
        	schedulerRepository.save(getJobInfo);
            schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " paused.");
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
        }
    }

    public void resumeJob(SchedulerJobInfoDTO jobInfo) {
        try {
        	SchedulerJobInfo getJobInfo = schedulerRepository.findByJobName(jobInfo.getJobName());
        	getJobInfo.setJobStatus("RESUMED");
        	schedulerRepository.save(getJobInfo);
            schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " resumed.");
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
        }
    }

    public void startJobNow(SchedulerJobInfoDTO jobInfo) {
        try {
        	SchedulerJobInfo getJobInfo = schedulerRepository.findByJobName(jobInfo.getJobName());
        	getJobInfo.setJobStatus("SCHEDULED & STARTED");
        	schedulerRepository.save(getJobInfo);
            schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled and started now.");
        } catch (SchedulerException e) {
            log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
        }
    }

	public void saveOrupdate(SchedulerJobInfoDTO scheduleJob) throws Exception {
		if (scheduleJob.getCronExpression().length() > 0) {
			scheduleJob.setJobClass(scheduleJob.getJobClass());
			scheduleJob.setCronJob(true);
		} else {
			scheduleJob.setJobClass(scheduleJob.getJobClass());
			scheduleJob.setCronJob(false);
			scheduleJob.setRepeatTime((long) 1);
		}
		if (scheduleJob.getJobId()==-1) {
			log.info("Job Info: {}", scheduleJob);
			scheduleNewJob(scheduleJob);
		} else {
			updateScheduleJob(scheduleJob);
		}
		scheduleJob.setDescription("i am job number " + scheduleJob.getJobId());
		scheduleJob.setInterfaceName("interface_" + scheduleJob.getJobId());
		log.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " created.");
	}

	@SuppressWarnings("unchecked")
	private void scheduleNewJob(SchedulerJobInfoDTO jobInfo) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
					.withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
			if (!scheduler.checkExists(jobDetail.getKey())) {

				jobDetail = scheduleCreator.createJob(
						(Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
						jobInfo.getJobName(), jobInfo.getJobGroup());

				Trigger trigger;
				if (jobInfo.getCronJob()) {
					trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
							jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				} else {
					trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
							jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				}
				scheduler.scheduleJob(jobDetail, trigger);
				jobInfo.setJobStatus("SCHEDULED");
				SchedulerJobInfo schedulerJobInfo = new SchedulerJobInfo();
				BeanUtils.copyProperties(jobInfo, schedulerJobInfo);
				schedulerRepository.save(schedulerJobInfo);
				log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled.");
			} else {
				log.error("scheduleNewJobRequest.jobAlreadyExist");
			}
		} catch (ClassNotFoundException e) {
			log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void updateScheduleJob(SchedulerJobInfoDTO jobInfo) {
		Trigger newTrigger;
		if (jobInfo.getCronJob()) {
			newTrigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
					jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		} else {
			newTrigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(), jobInfo.getRepeatTime(),
					SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		}
		try {
			schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getJobName()), newTrigger);
			jobInfo.setJobStatus("EDITED & SCHEDULED");
			SchedulerJobInfo schedulerJobInfo = new SchedulerJobInfo();
			BeanUtils.copyProperties(jobInfo, schedulerJobInfo);
			schedulerRepository.save(schedulerJobInfo);
			log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " updated and scheduled.");
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}
}
