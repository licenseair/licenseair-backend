package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* SessionLog
*/
@Data()
public class SessionLogModel {
  public Long id = null;

  /**
  *
  */
  public String sign = null;

  /**
  *
  */
  public Long user_id = null;

  /**
  *
  */
  public String key = null;

  /**
  * 1 user, 2 admin
  */
  public Integer type = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
