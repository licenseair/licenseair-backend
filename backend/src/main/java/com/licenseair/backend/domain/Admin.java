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
* Admin
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.admin")
public class Admin extends Model {
  @Id
  public Long id;

  /**
  * 是否删除
  */
  public boolean deleted = false;

  /**
  *
  */
  @Size(min = 0, max = 70, message = "长度不正确")
  @NotNull(message = "不能是空")
  @NotBlank(message = "不能是空")
  @NotEmpty(message = "不能是空")
  @Email(message = "邮箱格式不正确")
  public String email;

  /**
  *
  */
  @Size(min = 0, max = 11, message = "长度不正确")
  @NotNull(message = "不能是空")
  @NotBlank(message = "不能是空")
  @NotEmpty(message = "不能是空")
  @Unique(table = "public.admin", column = "mobile", message = "已经存在", groups = {Uni.class})
  public String mobile;

  /**
  *
  */
  @Size(min = 0, max = 60, message = "长度不正确")
  @NotNull(message = "不能是空")
  @NotBlank(message = "不能是空")
  @NotEmpty(message = "不能是空")
  public String password;

  /**
  * 是否激活
  */
  @NotNull(message = "是否激活")
  public boolean active = true;

  /**
  * 网站显示
  */
  @Size(min = 0, max = 32, message = "网站显示长度不正确")
  @NotNull(message = "网站显示不能是空")
  @NotBlank(message = "网站显示不能是空")
  @NotEmpty(message = "网站显示不能是空")
  public String username;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Admin> find = new Finder<>(Admin.class);

  public interface Uni{};
}


