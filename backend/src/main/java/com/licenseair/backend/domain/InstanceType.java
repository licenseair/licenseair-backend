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
import java.math.BigDecimal;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* InstanceType
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.instance_type")
public class InstanceType extends Model {
  @Id
  public Long id;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  * 名称
  */
  @NotNull(message = "名称不能是空")
  @NotBlank(message = "名称不能是空")
  @NotEmpty(message = "名称不能是空")
  public String name;

  /**
  * 规格
  */
  @NotNull(message = "规格不能是空")
  @NotBlank(message = "规格不能是空")
  @NotEmpty(message = "规格不能是空")
  @Unique(table = "public.instance_type", column = "type", message = "规格已经存在", groups = {Uni.class})
  public String type;

  /**
  * 价格（单位元）/小时
  */
  @Digits(integer = 10, fraction = 2, message = "价格（单位元）/小时长度不正确")
  @NotNull(message = "价格（单位元）/小时不能是空")
  public BigDecimal price = new BigDecimal("0.00");

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, InstanceType> find = new Finder<>(InstanceType.class);

  public interface Uni{};
}


