package com.licenseair.backend.domain;

import com.licenseair.backend.validation.isUnique.Unique;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.DbArray;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* SmsLog
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.sms_log")
public class SmsLog extends Model {
  @Id
  public Long id;

  /**
  *
  */
  public Integer deleted = 0;

  /**
  * 手机号
  */
  @Size(min = 0, max = 11, message = "手机号长度不正确")
  @NotNull(message = "手机号不能是空")
  @NotBlank(message = "手机号不能是空")
  @NotEmpty(message = "手机号不能是空")
  public String mobile;

  /**
  * 验证码
  */
  @NotNull(message = "验证码不能是空")
  public Integer code;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, SmsLog> find = new Finder<>(SmsLog.class);

  public interface Uni{};
}


