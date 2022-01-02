package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* AppInstance
*/
@Data()
public class AppInstanceModel {
  public Long id = null;

  /**
  * 是否删除
  */
  public Boolean deleted = null;

  /**
  * 是否删除
  */
  public Boolean auto_save = null;

  /**
  * 实例状态 Pending | Starting | Running ｜ Stopping
  */
  public String status = null;

  /**
  * 镜像id
  */
  public String image_id = null;

  /**
  * APPid
  */
  public Long application_id = null;

  /**
  * 公有地址
  */
  public String public_address = null;

  /**
  * 原有镜像id
  */
  public String origin_image_id = null;

  /**
  * 实例规格
  */
  public String instance_type = null;

  /**
  * 使用时长
  */
  public Integer hours = null;

  /**
  * 实例id
  */
  public String password = null;

  /**
  * 私有地址
  */
  public String private_address = null;

  /**
  * 用户id
  */
  public Long user_id = null;

  /**
  * 实例id
  */
  public String instance_id = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
