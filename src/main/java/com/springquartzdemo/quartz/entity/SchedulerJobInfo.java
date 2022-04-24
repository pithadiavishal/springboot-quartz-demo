package com.springquartzdemo.quartz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scheduler_job_info")
public class SchedulerJobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private int jobId;
    @Column(name = "job_name")
    private String jobName;
    @Column(name = "job_group")
    private String jobGroup;
    @Column(name = "job_status")
    private String jobStatus;
    @Column(name = "job_class")
    private String jobClass;
    @Column(name = "cron_expression")
    private String cronExpression;
    @Column(name = "description")
    private String description;
    @Column(name = "interface_name")
    private String interfaceName;
    @Column(name = "repeat_time")
    private Long repeatTime;
    @Column(name = "cron_job")
    private Boolean cronJob;
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String desciption) {
		this.description = desciption;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Long getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(Long repeatTime) {
		this.repeatTime = repeatTime;
	}
	public Boolean getCronJob() {
		return cronJob;
	}
	public void setCronJob(Boolean cronJob) {
		this.cronJob = cronJob;
	}
	@Override
	public String toString() {
		return "SchedulerJobInfo [jobId=" + jobId + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", jobStatus="
				+ jobStatus + ", jobClass=" + jobClass + ", cronExpression=" + cronExpression + ", description=" + description
				+ ", interfaceName=" + interfaceName + ", repeatTime=" + repeatTime + ", cronJob=" + cronJob + "]";
	}
    
}
