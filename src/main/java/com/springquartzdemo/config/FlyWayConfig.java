package com.springquartzdemo.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlyWayConfig {

	@Autowired
	@Qualifier("QuartzDataSource")
	private DataSource quartzDataSource;

	@Autowired
	private DataSource mainDataSource;

	@PostConstruct
	private void flyWayDataMigrate() {
		Flyway.configure().baselineOnMigrate(true).dataSource(mainDataSource).locations("db/migration/main").load()
				.migrate();
		Flyway.configure().baselineOnMigrate(true).dataSource(quartzDataSource).locations("db/migration/quartz").load()
				.migrate();
	}

}
