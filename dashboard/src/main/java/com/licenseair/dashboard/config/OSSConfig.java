package com.licenseair.dashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {


  public static String bookEndpoint;
  public static String imageEndpoint;

  @Value("${ali.OSS.bookEndpoint}")
  public void setBookEndpoint(String param) {
    bookEndpoint = param;
  }

  @Value("${ali.OSS.imageEndpoint}")
  public void setImageEndpoint(String param) {
    imageEndpoint = param;
  }
}
