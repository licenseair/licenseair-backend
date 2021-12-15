package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Language
*/
@Data()
public class LanguageModel {
  public Long id = null;

  /**
  *
  */
  public String tag = null;

  /**
  *
  */
  public Integer deleted = null;

  /**
  *
  */
  public String description = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
