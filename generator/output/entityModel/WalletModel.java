package com.licenseair.backend.domainModel;

import java.util.List;
import lombok.Data;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Wallet
*/
@Data()
public class WalletModel {
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
  * 余额
  */
  public BigDecimal money = null;

  public Timestamp created_at = null;

  public Timestamp updated_at = null;

}
