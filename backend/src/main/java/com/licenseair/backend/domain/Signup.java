package com.licenseair.backend.domain;

import com.licenseair.backend.validation.isUnique.Unique;
import io.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* Created by programschool.com
* User
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.user")
public class Signup extends Model implements Serializable {
  /**
  * 密码
  */
  @Size(min = 6, max = 60, message = "密码长度不正确")
  @NotBlank(message = "密码不能是空")
  @NotNull(message = "密码不能是空")
  @NotEmpty(message = "密码不能是空")
  public String password;

  /**
  * 手机号
  */
  @Size(min = 0, max = 11, message = "手机号长度不正确")
  @NotBlank(message = "手机号不能是空")
  @NotNull(message = "手机号不能是空")
  @NotEmpty(message = "手机号不能是空")
  @Unique(table = "public.user", column = "mobile", message = "手机号已经存在", groups = {User.Uni.class})
  public String mobile;

  /**
  * 验证码
  */
  @NotBlank(message = "验证码不能是空")
  @NotEmpty(message = "验证码不能是空")
  public Integer code;

  /**
  * 邮箱地址
  */
  @Size(min = 0, max = 70, message = "邮箱地址长度不正确")
  @Email(message = "邮箱格式不正确")
  public String email;

  /**
  * 用户名
  */
  @Size(min = 3, max = 32, message = "用户名长度不正确")
  @NotBlank(message = "用户名不能是空")
  @NotNull(message = "用户名不能是空")
  @NotEmpty(message = "用户名不能是空")
  @Unique(table = "public.user", column = "username", message = "用户名已经存在")
  public String username;

}


