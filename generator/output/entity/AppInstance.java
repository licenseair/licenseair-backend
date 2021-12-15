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
import java.sql.Timestamp;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* AppInstance
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.app_instance")
public class AppInstance extends Model {
  @Id
  public Long id;

  /**
  * 是否删除
  */
  public Integer deleted = 0;

  /**
  * 实例状态 Pending | Starting | Running ｜ Stopping
  */
  @Size(min = 0, max = 16, message = "实例状态 Pending | Starting | Running ｜ Stopping长度不正确")
  @NotNull(message = "实例状态 Pending | Starting | Running ｜ Stopping不能是空")
  @NotBlank(message = "实例状态 Pending | Starting | Running ｜ Stopping不能是空")
  @NotEmpty(message = "实例状态 Pending | Starting | Running ｜ Stopping不能是空")
  public String status;

  /**
  * 镜像id
  */
  @NotNull(message = "镜像id不能是空")
  @NotBlank(message = "镜像id不能是空")
  @NotEmpty(message = "镜像id不能是空")
  public String image_id;

  /**
  * APPid
  */
  @NotNull(message = "APPid不能是空")
  public Long application_id = 0L;

  /**
  * 公有地址
  */
  @Size(min = 0, max = 15, message = "公有地址长度不正确")
  @NotNull(message = "公有地址不能是空")
  @NotBlank(message = "公有地址不能是空")
  @NotEmpty(message = "公有地址不能是空")
  public String public_address;

  /**
  * 实例规格
  */
  @NotNull(message = "实例规格不能是空")
  @NotBlank(message = "实例规格不能是空")
  @NotEmpty(message = "实例规格不能是空")
  public String instance_type;

  /**
  * 原有镜像id
  */
  @NotNull(message = "原有镜像id不能是空")
  @NotBlank(message = "原有镜像id不能是空")
  @NotEmpty(message = "原有镜像id不能是空")
  public String origin_image_id;

  /**
  * 释放时间
  */
  public Timestamp remove_time;

  /**
  * 私有地址
  */
  @Size(min = 0, max = 15, message = "私有地址长度不正确")
  @NotNull(message = "私有地址不能是空")
  @NotBlank(message = "私有地址不能是空")
  @NotEmpty(message = "私有地址不能是空")
  public String private_address;

  /**
  * 用户id
  */
  @NotNull(message = "用户id不能是空")
  public Long user_id = 0L;

  /**
  * 实例id
  */
  @NotNull(message = "实例id不能是空")
  @NotBlank(message = "实例id不能是空")
  @NotEmpty(message = "实例id不能是空")
  public String instance_id;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, AppInstance> find = new Finder<>(AppInstance.class);

  public interface Uni{};
}


