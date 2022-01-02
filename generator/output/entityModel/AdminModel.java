package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Admin
*/
@Data()
public class AdminModel {
  public Long id = null;

  /**
  * 是否删除
  */
  public Boolean deleted = null;

  /**
  * 
  */
  public String email = null;

  /**
  * 
  */
  public String mobile = null;

  /**
  * 
  */
  public String password = null;

  /**
  * 是否激活
  */
  public Boolean active = null;

  /**
  * 网站显示
  */
  public String username = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
