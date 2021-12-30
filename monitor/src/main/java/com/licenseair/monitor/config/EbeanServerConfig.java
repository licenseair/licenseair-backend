package com.licenseair.monitor.config;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EbeanServerConfig {
  @Value("${datasource.db.username}")
  private String username;
  @Value("${datasource.db.password}")
  private String password;
  @Value("${datasource.db.url}")
  private String databaseUrl;
  @Value("${datasource.db.driver}")
  private String databaseDriver;

  @Bean
  public Database ebeanServer() {
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig.setUsername(this.username);
    dataSourceConfig.setPassword(this.password);
    dataSourceConfig.setUrl(this.databaseUrl);
    dataSourceConfig.setDriver(this.databaseDriver);

    // configuration ...
    DatabaseConfig config = new DatabaseConfig();
    config.setDataSourceConfig(dataSourceConfig);
    // create database instance
    return DatabaseFactory.create(config);
  }
}
