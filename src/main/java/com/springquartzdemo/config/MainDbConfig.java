package com.springquartzdemo.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "MainEntityManagerFactory",
  transactionManagerRef = "MainTransactionManager",
  basePackages = { "com.springquartzdemo.repository" }
)
public class MainDbConfig {
	
	@Bean(name="MainDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	@FlywayDataSource
	public DataSource primaryDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "MainEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("MainDataSource") DataSource primaryDataSource) {
		
		HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        LocalContainerEntityManagerFactoryBean em = builder
		.dataSource(primaryDataSource)
		.packages("com.springquartzdemo.entity")
		.build();
        em.setJpaPropertyMap(properties);
        
		return em;
	}

	@Primary
	@Bean(name = "MainTransactionManager")
	public PlatformTransactionManager primaryTransactionManager(
			@Qualifier("MainEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
		return new JpaTransactionManager(primaryEntityManagerFactory);
	}
}