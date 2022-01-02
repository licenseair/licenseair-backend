package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Boolean;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* SmsLog
*/
@Data()
public class SmsLogModel {
  public Long id = null;

  /**
  * 
  */
  public Boolean deleted = null;

  /**
  * 手机号
  */
  public String mobile = null;

  /**
  * 验证码
  */
  public Integer code = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
