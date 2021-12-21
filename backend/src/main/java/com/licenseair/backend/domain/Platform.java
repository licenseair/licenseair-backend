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
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Platform
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.platform")
public class Platform extends Model {
  @Id
  public Long id;

  /**
  *
  */
  public boolean deleted = false;

  /**
  *
  */
  @NotNull(message = "不能是空")
  @NotBlank(message = "不能是空")
  @NotEmpty(message = "不能是空")
  public String name;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Platform> find = new Finder<>(Platform.class);

  public interface Uni{};
}


