package com.licenseair.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

  @Value("${site.url}")
  private String siteUrl;
  /**
   * 跨域过滤器
   *
   * @return
   */
  @Bean
  public CorsFilter corsFilter() {
    //2.添加映射路径
    UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
    configSource.registerCorsConfiguration("/**", buildConfig());

    //3.返回新的CorsFilter.
    return new CorsFilter(configSource);
  }

  /**
   * 设置 CORS
   *
   * @return
   */
  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    //放行哪些原始域
    corsConfiguration.addAllowedOrigin(siteUrl);
    //是否发送Cookie信息
    corsConfiguration.setAllowCredentials(true);
    //放行哪些原始域(头部信息)
    corsConfiguration.addAllowedHeader("*");
    //放行哪些原始域(请求方式)
    List<String> methods = List.of("GET", "POST", "OPTION");
    corsConfiguration.setAllowedMethods(methods);
    //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
    // corsConfiguration.addExposedHeader("**");
    corsConfiguration.addExposedHeader("Content-Type");
    corsConfiguration.addExposedHeader("X-Requested-With");
    corsConfiguration.addExposedHeader("accept");
    corsConfiguration.addExposedHeader("Origin");
    corsConfiguration.addExposedHeader("Access-Control-Request-Method");
    corsConfiguration.addExposedHeader("Access-Control-Request-Headers");

    return corsConfiguration;
  }
}
