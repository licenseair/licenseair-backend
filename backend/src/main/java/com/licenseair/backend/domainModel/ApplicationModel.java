package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.math.BigDecimal;
import java.lang.Boolean;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Application
*/
@Data()
public class ApplicationModel {
  public Long id = null;

  /**
  * 名称
  */
  public String name = null;

  /**
  * 分类
  */
  public List<String> category = null;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  * 支持系统平台
  */
  public List<String> platform = null;

  /**
  * 支持语言
  */
  public List<String> languages = null;

  /**
  * 简介
  */
  public String introduce = null;

  /**
  * 价格
  */
  public BigDecimal price = null;

  /**
  * 当前版本
  */
  public String version = null;

  /**
  * 图标
  */
  public String icon = null;

  /**
  * 可用状态
  */
  public Boolean usable = null;

  /**
  * 官方价格
  */
  public BigDecimal official_price = null;

  /**
  * 标签
  */
  public List<String> tags = null;

  /**
  * 路径
  */
  public String path = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
