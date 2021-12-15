package com.licenseair.dashboard.commons.model;

public class DeleteResponse extends QueryResponse {
  public final String message = "已经删除";
  public DeleteResponse(Boolean object) {
    super(object);
  }
}
