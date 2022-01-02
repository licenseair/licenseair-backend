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
import java.lang.Long;
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* InstanceImage
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.instance_image")
public class InstanceImage extends Model {
  @Id
  public Long id;

  /**
  * 
  */
  @NotNull(message = "不能是空")
  public Boolean deleted;

  /**
  * 忙碌 false为可用
  */
  @NotNull(message = "忙碌 false为可用不能是空")
  public Boolean busy;

  /**
  * 
  */
  @NotNull(message = "不能是空")
  public Long application_id = 0L;

  /**
  * 镜像 ID
  */
  @NotNull(message = "镜像 ID不能是空")
  @NotBlank(message = "镜像 ID不能是空")
  @NotEmpty(message = "镜像 ID不能是空")
  @Unique(table = "public.instance_image", column = "image_id", message = "镜像 ID已经存在", groups = {Uni.class})
  public String image_id;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, InstanceImage> find = new Finder<>(InstanceImage.class);

  public interface Uni{};
}


