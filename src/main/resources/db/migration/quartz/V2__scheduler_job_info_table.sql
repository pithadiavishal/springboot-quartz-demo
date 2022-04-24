create table scheduler_job_info (
	job_id integer not null auto_increment, 
	cron_expression varchar(255), 
	cron_job bit, 
	description varchar(255), 
	interface_name varchar(255), 
	job_class varchar(255), 
	job_group varchar(255), 
	job_name varchar(255), 
	job_status varchar(255), 
	repeat_time bigint, 
	primary key (job_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
