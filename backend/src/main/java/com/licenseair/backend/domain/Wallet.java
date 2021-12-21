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

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Wallet
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.wallet")
public class Wallet extends Model {
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
  @Unique(table = "public.wallet", column = "user_id", message = "已经存在", groups = {Uni.class})
  public Long user_id = 0L;

  /**
  * 余额
  */
  @Digits(integer = 10, fraction = 2, message = "余额长度不正确")
  @NotNull(message = "余额不能是空")
  @NotBlank(message = "余额不能是空")
  @NotEmpty(message = "余额不能是空")
  public BigDecimal money = new BigDecimal(0.00);

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Wallet> find = new Finder<>(Wallet.class);

  public interface Uni{};
}


