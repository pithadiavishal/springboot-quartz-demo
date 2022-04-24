package com.springquartzdemo.quartz.controller;

import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springquartzdemo.quartz.dto.SchedulerJobInfoDTO;
import com.springquartzdemo.quartz.service.ScheduleQuartzJobManager;

@RestController
public class ScheduleQuartzJobController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ScheduleQuartzJobManager scheduleJobService;

	@PostMapping("/save-update")
	public void saveOrUpdate(@RequestBody SchedulerJobInfoDTO job) {
		log.info("params, job = {}", job);
		try {
			scheduleJobService.saveOrupdate(job);
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
	}

	@GetMapping("/meta-data")
	public Object metaData() throws SchedulerException {
		SchedulerMetaData metaData = scheduleJobService.getMetaData();
		return metaData;
	}

	@GetMapping("/get-all-jobs")
	public Object getAllJobs() throws SchedulerException {
		return scheduleJobService.getAllJobList().stream().map(SchedulerJobInfoDTO::new);
	}

	@PostMapping("/run-job")
	public void runJob(@RequestBody SchedulerJobInfoDTO job) {
		log.info("params, job = {}", job);
		try {
			scheduleJobService.startJobNow(job);
		} catch (Exception e) {
			log.error("runJob ex:", e);
		}
	}

	@PostMapping("/pause-job")
	public void pauseJob(@RequestBody SchedulerJobInfoDTO job) {
		log.info("params, job = {}", job);
		try {
			scheduleJobService.pauseJob(job);
		} catch (Exception e) {
			log.error("pauseJob ex:", e);
		}
	}

	@PostMapping("/resume-job")
	public void resumeJob(@RequestBody SchedulerJobInfoDTO job) {
		log.info("params, job = {}", job);
		try {
			scheduleJobService.resumeJob(job);
		} catch (Exception e) {
			log.error("resumeJob ex:", e);
		}
	}

	@DeleteMapping("/delete-job")
	public void deleteJob(@RequestBody SchedulerJobInfoDTO job) {
		log.info("params, job = {}", job);
		try {
			scheduleJobService.deleteJob(job);
		} catch (Exception e) {
			log.error("deleteJob ex:", e);
		}
	}
}
