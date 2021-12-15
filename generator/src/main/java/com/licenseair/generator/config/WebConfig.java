package com.licenseair.generator.config;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class WebConfig implements ViewResolver {
  @Override
  public View resolveViewName(String viewName, Locale locale) throws Exception {
    return null;
  }
}
