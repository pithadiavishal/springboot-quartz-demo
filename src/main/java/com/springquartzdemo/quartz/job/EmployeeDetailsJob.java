package com.springquartzdemo.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.springquartzdemo.service.EmployeeServiceManager;


@DisallowConcurrentExecution
public class EmployeeDetailsJob extends QuartzJobBean {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmployeeServiceManager employeeService;
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		employeeService.getAllEmployee().forEach(System.out::println);
	}
}
