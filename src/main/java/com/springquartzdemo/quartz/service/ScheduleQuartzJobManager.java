package com.springquartzdemo.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;

import com.springquartzdemo.quartz.dto.SchedulerJobInfoDTO;
import com.springquartzdemo.quartz.entity.SchedulerJobInfo;

public interface ScheduleQuartzJobManager {

	void saveOrupdate(SchedulerJobInfoDTO job) throws Exception;

	SchedulerMetaData getMetaData() throws SchedulerException;

	List<SchedulerJobInfo> getAllJobList();

	void startJobNow(SchedulerJobInfoDTO job);

	void pauseJob(SchedulerJobInfoDTO job);

	void resumeJob(SchedulerJobInfoDTO job);

	void deleteJob(SchedulerJobInfoDTO job);
	
}
