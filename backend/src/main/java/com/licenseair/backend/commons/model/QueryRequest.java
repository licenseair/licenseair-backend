package com.licenseair.backend.commons.model;

import lombok.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryRequest {
  public QueryModel query = null;

  @Min(value = 0, message = "分页必须从1开始")
  public Integer page = 0;

  @Max(value = 50, message = "每次最多查询50条数据。")
  public Integer pageSize = 20; // 默认20条

  public List<String> columns = List.of("*");

  public class QueryModel {
    // =
    public List<Map<String, Object>> eq = null;
    // !=
    public List<Map<String, Object>> ne = null;
    // >=
    public List<Map<String, Object>> le = null;
    // >
    public List<Map<String, Object>> lt = null;
    // <=
    public List<Map<String, Object>> ge = null;
    // <
    public List<Map<String, Object>> gt = null;
    public arrayContainsType arrayContains = null;

    public ArrayList<String> order = null;

    public List<String> group = null;
  }

  public class arrayContainsType {
    public String field;
    public List<String> array;
  }
}
