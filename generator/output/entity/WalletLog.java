package com.licenseair.backend.domain;

import com.licenseair.backend.validation.isUnique.Unique;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.DbArray;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

import java.lang.String;
import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* WalletLog
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.wallet_log")
public class WalletLog extends Model {
  @Id
  public Long id;

  /**
  *
  */
  public boolean deleted = false;

  /**
  *
  */
  @NotNull(message = "不能是空")
  public Long user_id = 0L;

  /**
  * 资金变动
  */
  @Digits(integer = 10, fraction = 2, message = "资金变动长度不正确")
  @NotNull(message = "资金变动不能是空")
  @NotBlank(message = "资金变动不能是空")
  @NotEmpty(message = "资金变动不能是空")
  public BigDecimal money = new BigDecimal(0.00);

  /**
  * 资金变动
  */
  @NotNull(message = "资金变动不能是空")
  @NotBlank(message = "资金变动不能是空")
  @NotEmpty(message = "资金变动不能是空")
  public String description;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, WalletLog> find = new Finder<>(WalletLog.class);

  public interface Uni{};
}


