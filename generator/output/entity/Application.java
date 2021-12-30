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
import java.math.BigDecimal;
import java.lang.Boolean;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Application
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.application")
public class Application extends Model {
  @Id
  public Long id;

  /**
  * 名称
  */
  @NotNull(message = "名称不能是空")
  @NotBlank(message = "名称不能是空")
  @NotEmpty(message = "名称不能是空")
  @Unique(table = "public.application", column = "name", message = "名称已经存在", groups = {Uni.class})
  public String name;

  /**
  * 分类
  */
  @NotNull(message = "分类不能是空")
  @DbArray
  public List<String> category = new ArrayList<>();

  /**
  * 支持系统平台
  */
  @NotNull(message = "支持系统平台不能是空")
  @DbArray
  public List<String> platform = new ArrayList<>();

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  * 支持语言
  */
  @NotNull(message = "支持语言不能是空")
  @DbArray
  public List<String> languages = new ArrayList<>();

  /**
  * 简介
  */
  @NotNull(message = "简介不能是空")
  @NotBlank(message = "简介不能是空")
  @NotEmpty(message = "简介不能是空")
  public String introduce;

  /**
  * 价格
  */
  @Digits(integer = 10, fraction = 2, message = "价格长度不正确")
  public BigDecimal price = new BigDecimal(0.00);

  /**
  * 当前版本
  */
  @NotNull(message = "当前版本不能是空")
  @NotBlank(message = "当前版本不能是空")
  @NotEmpty(message = "当前版本不能是空")
  public String version;

  /**
  * 图标
  */
  @Size(min = 0, max = 42, message = "图标长度不正确")
  @NotNull(message = "图标不能是空")
  @NotBlank(message = "图标不能是空")
  @NotEmpty(message = "图标不能是空")
  public String icon;

  /**
  * 可用状态
  */
  @NotNull(message = "可用状态不能是空")
  public Boolean usable;

  /**
  * 官方价格
  */
  @Digits(integer = 10, fraction = 2, message = "官方价格长度不正确")
  public BigDecimal official_price = new BigDecimal(0.00);

  /**
  * 标签
  */
  @DbArray
  public List<String> tags = new ArrayList<>();

  /**
  * 路径
  */
  @NotNull(message = "路径不能是空")
  @NotBlank(message = "路径不能是空")
  @NotEmpty(message = "路径不能是空")
  @Unique(table = "public.application", column = "path", message = "路径已经存在", groups = {Uni.class})
  public String path;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Application> find = new Finder<>(Application.class);

  public interface Uni{};
}


