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
* Auth2
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.auth2")
public class Auth2 extends Model {
  @Id
  public Long id;

  /**
  *
  */
  public boolean deleted = false;

  /**
  * unionid
  */
  public String unionid;

  /**
  *
  */
  @NotNull(message = "不能是空")
  public Long user_id = 0L;

  /**
  * uuid
  */
  @NotNull(message = "uuid不能是空")
  @NotBlank(message = "uuid不能是空")
  @NotEmpty(message = "uuid不能是空")
  public String uuid;

  /**
  * 认证来源
  */
  @NotNull(message = "认证来源不能是空")
  @NotBlank(message = "认证来源不能是空")
  @NotEmpty(message = "认证来源不能是空")
  public String source;

  /**
  *
  */
  @NotNull(message = "不能是空")
  @NotBlank(message = "不能是空")
  @NotEmpty(message = "不能是空")
  public String raw_data;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Auth2> find = new Finder<>(Auth2.class);

  public interface Uni{};
}


