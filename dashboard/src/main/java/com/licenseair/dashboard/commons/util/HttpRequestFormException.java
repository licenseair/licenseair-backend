package com.licenseair.dashboard.commons.util;

import java.util.Map;

@SuppressWarnings("serial")
public class HttpRequestFormException extends Exception {
  private final int statusCode;
  private final Map<String, String> failedFields;

  public HttpRequestFormException(final int statusCode, final Map<String, String> failedFields) {
    super("输入数据错误");
    this.failedFields = failedFields;
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public Map<String, String> getFailedFields() { return this.failedFields; }
}
