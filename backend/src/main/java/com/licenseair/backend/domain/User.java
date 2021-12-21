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
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* User
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.user")
public class User extends Model {
  @Id
  public Long id;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  * 邮箱地址
  */
  @Size(min = 0, max = 70, message = "邮箱地址长度不正确")
  @Email(message = "邮箱格式不正确")
  public String email;

  /**
  * 手机号
  */
  @Size(min = 0, max = 11, message = "手机号长度不正确")
  @NotNull(message = "手机号不能是空")
  @NotBlank(message = "手机号不能是空")
  @NotEmpty(message = "手机号不能是空")
  @Unique(table = "public.user", column = "mobile", message = "手机号已经存在", groups = {Uni.class})
  public String mobile;

  /**
  * 密码
  */
  @Size(min = 0, max = 60, message = "密码长度不正确")
  @NotNull(message = "密码不能是空")
  @NotBlank(message = "密码不能是空")
  @NotEmpty(message = "密码不能是空")
  public String password;

  /**
  * 用户域名
  */
  @Size(min = 0, max = 32, message = "用户域名长度不正确")
  @Unique(table = "public.user", column = "domain", message = "用户域名已经存在", groups = {Uni.class})
  public String domain;

  /**
  * 是否激活
  */
  @NotNull(message = "是否激活")
  public boolean active = true;

  /**
  * 用户名
  */
  @Size(min = 0, max = 32, message = "用户名长度不正确")
  @NotNull(message = "用户名不能是空")
  @NotBlank(message = "用户名不能是空")
  @NotEmpty(message = "用户名不能是空")
  @Unique(table = "public.user", column = "username", message = "用户名已经存在", groups = {Uni.class})
  public String username;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, User> find = new Finder<>(User.class);

  public interface Uni{};
}


