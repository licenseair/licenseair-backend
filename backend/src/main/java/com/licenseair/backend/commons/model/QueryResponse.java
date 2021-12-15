package com.licenseair.backend.commons.model;

import com.google.gson.Gson;
import io.ebean.Model;
import org.springframework.http.HttpStatus;
import java.util.Map;

public class QueryResponse {
  private final int error = 0;
  private final int code = HttpStatus.OK.value();
  private final Object data;

  public QueryResponse(DataResource object) {
    this.data = object;
  }

  public QueryResponse(String object) {
    Gson gson = new Gson();
    this.data = gson.fromJson(object, Map.class);
  }

  public QueryResponse(Map object) {
    this.data = object;
  }

  public QueryResponse(Model object) {
    this.data = object;
  }

  public QueryResponse(Boolean object) {
    this.data = Map.of("deleted", object);
  }
}
