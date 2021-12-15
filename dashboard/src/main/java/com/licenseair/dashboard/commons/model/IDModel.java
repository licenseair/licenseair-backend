package com.licenseair.dashboard.commons.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class IDModel {
  @NotNull(message = "id 必须设置")
  public Long id;
}
