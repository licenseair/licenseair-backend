package com.licenseair.backend.commons.model;

import com.google.gson.Gson;
import io.ebean.Model;
import org.springframework.http.HttpStatus;
import java.util.Map;

public class QueryResponse {
  /**
   * 是否存在错误 =0:不存在
   */
  public final int error = 0;

  /**
   * 错误号
   */
  public final int code = HttpStatus.OK.value();

  /**
   * 扩展信息
   */
  public String extra = null;

  /**
   * 返回数据
   */
  public final Object data;

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
