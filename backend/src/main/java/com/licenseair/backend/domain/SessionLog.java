package com.licenseair.backend.domain;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* SessionLog
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.session_log")
public class SessionLog extends Model {
  @Id
  public Long id;

  /**
  *
  */
  public String sign;

  /**
  *
  */
  public Long user_id;

  /**
  *
  */
  @Size(min = 0, max = 44, message = "长度不正确")
  public String key;

  /**
  * 1 user, 2 admin
  */
  public Integer type = 1;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, SessionLog> find = new Finder<>(SessionLog.class);

  public interface Uni{};
}


