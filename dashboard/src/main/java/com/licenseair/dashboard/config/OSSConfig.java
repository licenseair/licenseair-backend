package com.licenseair.dashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {
  public static String bookEndpoint;
  public static String imageEndpoint;
  public static String accessKeyId;
  public static String accessKeySecret;

  @Value("${ali.OSS.bookEndpoint}")
  public void setBookEndpoint(String param) {
    bookEndpoint = param;
  }

  @Value("${ali.OSS.imageEndpoint}")
  public void setImageEndpoint(String param) {
    imageEndpoint = param;
  }

  @Value("${ali.OSS.accessKeyId}")
  public void setAccessKeyId(String param) {
    accessKeyId = param;
  }

  @Value("${ali.OSS.accessKeySecret}")
  public void setAccessKeySecret(String param) {
    accessKeySecret = param;
  }
}
