package com.licenseair.backend.domain;

import io.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.*;

@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
public class Login extends Model {
  @NotBlank(message = "请输入账号")
  @NotNull
  public String account;

  @NotBlank(message = "请输入密码")
  @NotNull
  public String password;
}
