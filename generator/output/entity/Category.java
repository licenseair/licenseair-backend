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
import java.lang.Boolean;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Category
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.category")
public class Category extends Model {
  @Id
  public Long id;

  /**
  * 是否删除
  */
  @NotNull(message = "是否删除不能是空")
  public Boolean deleted;

  /**
  * 分类名称
  */
  @NotNull(message = "分类名称不能是空")
  @NotBlank(message = "分类名称不能是空")
  @NotEmpty(message = "分类名称不能是空")
  public String name;

  /**
  * 英文名称
  */
  @NotNull(message = "英文名称不能是空")
  @NotBlank(message = "英文名称不能是空")
  @NotEmpty(message = "英文名称不能是空")
  @Unique(table = "public.category", column = "path", message = "英文名称已经存在", groups = {Uni.class})
  public String path;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Category> find = new Finder<>(Category.class);

  public interface Uni{};
}


