package com.licenseair.backend.commons.util;

@SuppressWarnings("serial")
public class HttpRequestException extends Exception {

  private final int statusCode;
  private final String message;

  public HttpRequestException(final int statusCode, final String message) {
    super(message);
    this.statusCode = statusCode;
    this.message = message;
  }

  public int getStatusCode() {
    return this.statusCode;
  }
}
