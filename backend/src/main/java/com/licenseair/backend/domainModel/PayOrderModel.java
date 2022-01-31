package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Boolean;
import java.sql.Timestamp;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* PayOrder
*/
@Data()
public class PayOrderModel {
  public Long id = null;

  /**
  * 快照
  */
  public String snapshot = null;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  * 订单号
  */
  public String trade_no = null;

  /**
  * 价格（单位元）
  */
  public BigDecimal price = null;

  /**
  * subject id
  */
  public Long subject_id = null;

  /**
  * 支付时间
  */
  public Timestamp pay_time = null;

  /**
  * 是否已经支付
  */
  public Boolean is_pay = null;

  /**
  * 用户id
  */
  public Long user_id = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
