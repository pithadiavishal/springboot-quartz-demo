package com.springquartzdemo.quartz.dto;

import org.springframework.beans.BeanUtils;

import com.springquartzdemo.quartz.entity.SchedulerJobInfo;

public class SchedulerJobInfoDTO {

    private int jobId;
    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String jobClass;
    private String cronExpression;
    private String description;    
    private String interfaceName;
    private Long repeatTime;
    private Boolean cronJob;
    public SchedulerJobInfoDTO() {
		
	}
    public SchedulerJobInfoDTO(SchedulerJobInfo jobInfo) {
    	BeanUtils.copyProperties(jobInfo, this);
	}
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
