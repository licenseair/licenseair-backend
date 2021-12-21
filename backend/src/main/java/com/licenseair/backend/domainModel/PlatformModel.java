package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Platform
*/
@Data()
public class PlatformModel {
  public Long id = null;

  /**
  *
  */
  public boolean deleted = false;

  /**
  *
  */
  public String name = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
