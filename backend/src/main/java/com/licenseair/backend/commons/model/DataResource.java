package com.licenseair.backend.commons.model;

import java.util.List;

public class DataResource<T> {
  public final int pageTotal;
  public final int dataTotal;
  public final int currentPage;
  public final int pageSize;
  public final List<T> list;

  /**
   * @param pageTotal
   * @param dataTotal
   * @param currentPage
   * @param pageSize
   * @param dataList
   */
  public DataResource(int pageTotal, int dataTotal, int currentPage, int pageSize, List<T> dataList) {
    this.list = dataList;
    this.pageTotal = pageTotal;
    this.dataTotal = dataTotal;
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }
}
