package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* User
*/
@Data()
public class UserModel {
  public Long id = null;

  /**
  * 是否删除
  */
  public Boolean deleted = null;

  /**
  * 邮箱地址
  */
  public String email = null;

  /**
  * 手机号
  */
  public String mobile = null;

  /**
  * 密码
  */
  public String password = null;

  /**
  * 用户域名
  */
  public String domain = null;

  /**
  * 是否激活
  */
  public Boolean active = null;

  /**
  * 用户名
  */
  public String username = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
