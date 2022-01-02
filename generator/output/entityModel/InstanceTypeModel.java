package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.math.BigDecimal;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* InstanceType
*/
@Data()
public class InstanceTypeModel {
  public Long id = null;

  /**
  * 是否删除
  */
  public Boolean deleted = null;

  /**
  * 名称
  */
  public String name = null;

  /**
  * 规格
  */
  public String type = null;

  /**
  * 价格（单位元）/小时
  */
  public BigDecimal price = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
