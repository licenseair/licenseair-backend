package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Auth2
*/
@Data()
public class Auth2Model {
  public Long id = null;

  /**
  *
  */
  public boolean deleted = false;

  /**
  * unionid
  */
  public String unionid = null;

  /**
  *
  */
  public Long user_id = null;

  /**
  * uuid
  */
  public String uuid = null;

  /**
  * 认证来源
  */
  public String source = null;

  /**
  *
  */
  public String raw_data = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
