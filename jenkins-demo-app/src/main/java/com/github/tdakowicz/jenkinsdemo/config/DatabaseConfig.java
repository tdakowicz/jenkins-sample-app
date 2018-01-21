package com.github.tdakowicz.jenkinsdemo.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	@Bean
	@ConfigurationProperties(prefix = "datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
}
