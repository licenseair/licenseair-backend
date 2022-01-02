package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* City
*/
@Data()
public class CityModel {
  public Long id = null;

  /**
  * 
  */
  public String province = null;

  /**
  * 
  */
  public Boolean deleted = null;

  /**
  * 
  */
  public String city = null;

  /**
  * 
  */
  public String join_name = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
