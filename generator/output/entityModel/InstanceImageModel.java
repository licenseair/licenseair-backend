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
* InstanceImage
*/
@Data()
public class InstanceImageModel {
  public Long id = null;

  /**
  *
  */
  public Integer deleted = null;

  /**
  * 忙碌 false为可用
  */
  public Boolean busy = null;

  /**
  *
  */
  public Long application_id = null;

  /**
  * 镜像 ID
  */
  public String image_id = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
