package com.licenseair.dashboard.commons.model;

public class BadRequest {
  public final int error = 1;
  public final int code;
  public final String message;

  /**
   * @param status
   * @param msg
   */
  public BadRequest(int status, String msg) {
    code = status;
    message = msg;
  }
}
