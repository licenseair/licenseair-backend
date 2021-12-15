package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Category
*/
@Data()
public class CategoryModel {
  public Long id = null;

  /**
  * 是否删除
  */
  public Integer deleted = null;

  /**
  * 分类名称
  */
  public String name = null;

  /**
  * 英文名称
  */
  public String path = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
