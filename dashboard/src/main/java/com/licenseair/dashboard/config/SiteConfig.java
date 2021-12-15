package com.licenseair.dashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteConfig {
  public static String AppName;

  @Value("${spring.application.name}")
  public void setAppName(String param) {
    AppName = param;
  }

}
