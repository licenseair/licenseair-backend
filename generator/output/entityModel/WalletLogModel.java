package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.String;
import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* WalletLog
*/
@Data()
public class WalletLogModel {
  public Long id = null;

  /**
  * 
  */
  public Boolean deleted = null;

  /**
  * 
  */
  public Long user_id = null;

  /**
  * 资金变动
  */
  public BigDecimal money = null;

  /**
  * 资金变动
  */
  public String description = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
