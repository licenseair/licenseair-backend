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
import javax.persistence.Transient;
import javax.validation.constraints.*;

import java.lang.Long;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;

/**
 * Created by programschool.com
 * User
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "public.user")
public class UserReadOnly extends Model {
  @Id
  public Long id;

  /**
   * 密码
   */
  @Transient
  public String password;

  /**
   * vip 过期时间
   */
  public Timestamp vip_expired;

  /**
   * 是否删除
   */
  public Integer deleted = 0;

  /**
   * 会员类型
   */
  public Long vip_plan_id = 0L;

  /**
   * 用户域名
   */
  @Size(min = 3, max = 32, message = "用户域名长度不正确")
  @NotBlank(message = "用户域名不能是空")
  @NotNull(message = "用户域名不能是空")
  @NotEmpty(message = "用户域名不能是空")
  @Unique(table = "public.user", column = "domain", message = "用户域名已经存在", groups = {Uni.class})
  public String domain;

  /**
   * 手机号
   */
  @Size(min = 0, max = 11, message = "手机号长度不正确")
  @NotBlank(message = "手机号不能是空")
  @NotNull(message = "手机号不能是空")
  @NotEmpty(message = "手机号不能是空")
  @Unique(table = "public.user", column = "mobile", message = "手机号已经存在", groups = {Uni.class})
  public String mobile;

  /**
   * 0未激活，1激活
   */
  @NotNull(message = "0未激活，1激活不能是空")
  public Integer active = 0;

  /**
   * 邮箱地址
   */
  @Size(min = 0, max = 70, message = "邮箱地址长度不正确")
  @Email(message = "邮箱格式不正确")
  public String email;

  /**
   * 用户名
   */
  @Size(min = 0, max = 32, message = "用户名长度不正确")
  @NotBlank(message = "用户名不能是空")
  @NotNull(message = "用户名不能是空")
  @NotEmpty(message = "用户名不能是空")
  @Unique(table = "public.user", column = "username", message = "用户名已经存在", groups = {Uni.class})
  public String username;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, UserReadOnly> find = new Finder<>(UserReadOnly.class);

  public interface Uni{};
}
