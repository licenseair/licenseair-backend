package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Long;
import java.sql.Date;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Profile
*/
@Data()
public class ProfileModel {
  public Long id = null;

  /**
  * 是否删除
  */
  public Integer deleted = null;

  /**
  * 公司
  */
  public String company = null;

  /**
  * 昵称
  */
  public String nickname = null;

  /**
  * 用户id
  */
  public Long user_id = null;

  /**
  * 头像
  */
  public String avatar = null;

  /**
  * 生日
  */
  public Date birthday = null;

  /**
  * 简介
  */
  public String intro = null;

  /**
  * 所在城市
  */
  public String city = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
