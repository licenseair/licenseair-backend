package com.licenseair.backend.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Configuration
class WebConfig extends WebMvcConfigurationSupport {
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(converter());
    addDefaultHttpMessageConverters(converters);
  }

  @Bean
  GsonHttpMessageConverter converter() {
    //do your customizations here...
    // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
    gsonConverter.setGson(gson);

    return gsonConverter;
  }

  // @Override
  // public void configurePathMatch(PathMatchConfigurer configurer) { }
}
