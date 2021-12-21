package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Integer;
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
  public boolean deleted = false;

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
  * 0未激活，1激活
  */
  public Integer active = null;

  /**
  * 用户名
  */
  public String username = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
