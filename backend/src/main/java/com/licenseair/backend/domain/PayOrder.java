package com.licenseair.backend.domain;

import com.licenseair.backend.validation.isUnique.Unique;
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
import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Boolean;
import java.sql.Timestamp;
import java.lang.Integer;

/**
* Created by licenseair.com
* PayOrder
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.pay_order")
public class PayOrder extends Model {
  @Id
  public Long id;

  /**
  * 快照
  */
  public String snapshot;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  * 订单号
  */
  @Size(min = 0, max = 128, message = "订单号长度不正确")
  @NotNull(message = "订单号不能是空")
  @NotBlank(message = "订单号不能是空")
  @NotEmpty(message = "订单号不能是空")
  @Unique(table = "public.pay_order", column = "trade_no", message = "订单号已经存在", groups = {Uni.class})
  public String trade_no;

  /**
  * 价格（单位元）
  */
  @Digits(integer = 10, fraction = 2, message = "价格（单位元）长度不正确")
  @NotNull(message = "价格（单位元）不能是空")
  @NotBlank(message = "价格（单位元）不能是空")
  @NotEmpty(message = "价格（单位元）不能是空")
  public BigDecimal price = new BigDecimal(0.00);

  /**
  * subject id
  */
  @NotNull(message = "subject id不能是空")
  public Long subject_id;

  /**
  * 支付时间
  */
  public Timestamp pay_time;

  /**
  * 是否已经支付
  */
  @NotNull(message = "是否已经支付不能是空")
  public Boolean is_pay;

  /**
  * 用户id
  */
  @NotNull(message = "用户id不能是空")
  public Long user_id;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, PayOrder> find = new Finder<>(PayOrder.class);

  public interface Uni{};
}


