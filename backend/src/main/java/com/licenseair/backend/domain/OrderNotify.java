package com.licenseair.backend.domain;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.lang.String;
import java.sql.Timestamp;
import java.lang.Integer;

/**
* Created by licenseair.com
* OrderNotify
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.order_notify")
public class OrderNotify extends Model {
  @Id
  public Long id;

  /**
  *
  */
  @Size(min = 0, max = 32, message = "长度不正确")
  public String method;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  *
  */
  public String sign;

  /**
  *
  */
  @Size(min = 0, max = 32, message = "长度不正确")
  public String trade_no;

  /**
  *
  */
  @Size(min = 0, max = 16, message = "长度不正确")
  public String charset;

  /**
  *
  */
  @Size(min = 0, max = 16, message = "长度不正确")
  public String auth_app_id;

  /**
  *
  */
  @Size(min = 0, max = 64, message = "长度不正确")
  public String out_trade_no;

  /**
  *
  */
  public Timestamp timestamp;

  /**
  *
  */
  @Size(min = 0, max = 16, message = "长度不正确")
  public String app_id;

  /**
  *
  */
  @Size(min = 0, max = 4, message = "长度不正确")
  public String version;

  /**
  *
  */
  @Size(min = 0, max = 16, message = "长度不正确")
  public String seller_id;

  /**
  *
  */
  @Size(min = 0, max = 255, message = "长度不正确")
  public String total_amount;

  /**
  *
  */
  @Size(min = 0, max = 4, message = "长度不正确")
  public String sign_type;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, OrderNotify> find = new Finder<>(OrderNotify.class);

  public interface Uni{};
}


