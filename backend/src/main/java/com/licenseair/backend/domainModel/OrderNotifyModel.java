package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.sql.Timestamp;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* OrderNotify
*/
@Data()
public class OrderNotifyModel {
  public Long id = null;

  /**
  *
  */
  public String method = null;

  /**
  * 是否删除
  */
  public Integer deleted = null;

  /**
  *
  */
  public String sign = null;

  /**
  *
  */
  public String trade_no = null;

  /**
  *
  */
  public String charset = null;

  /**
  *
  */
  public String auth_app_id = null;

  /**
  *
  */
  public String out_trade_no = null;

  /**
  *
  */
  public Timestamp timestamp = null;

  /**
  *
  */
  public String app_id = null;

  /**
  *
  */
  public String version = null;

  /**
  *
  */
  public String seller_id = null;

  /**
  *
  */
  public String total_amount = null;

  /**
  *
  */
  public String sign_type = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
