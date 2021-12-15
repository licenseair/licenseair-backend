package com.licenseair.backend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Data()
@Entity()
public class Security {

  @NotEmpty(message = "密码不能是空")
  public String mobile;

  @NotEmpty(message = "密码不能是空")
  public String password;

  @NotEmpty(message = "密码不能是空")
  public Integer code;
}
