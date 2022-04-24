package com.springquartzdemo.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "QuartzEntityManagerFactory",
  transactionManagerRef = "QuartzTransactionManager",
  basePackages = { "com.springquartzdemo.quartz.repository" }
)
public class QuartzDbConfig {
	
	@Bean(name="QuartzDataSource")
	@ConfigurationProperties(prefix="spring.seconddatasource")
	public DataSource primaryDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Bean(name = "QuartzEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("QuartzDataSource") DataSource primaryDataSource) {
		HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        LocalContainerEntityManagerFactoryBean em =  builder
				.dataSource(primaryDataSource)
				.packages("com.springquartzdemo.quartz.entity")
				.build();
        em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean(name = "QuartzTransactionManager")
	public PlatformTransactionManager primaryTransactionManager(
			@Qualifier("QuartzEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
		return new JpaTransactionManager(primaryEntityManagerFactory);
	}
}