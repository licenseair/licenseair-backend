package com.licenseair.backend.domain;

import com.licenseair.backend.validation.isUnique.Unique;
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
import java.sql.Date;
import java.lang.Integer;
import java.sql.Timestamp;

/**
* Created by licenseair.com
* Profile
*/
@Data()
@EqualsAndHashCode(callSuper=true)
@Entity()
@Table(name = "public.profile")
public class Profile extends Model {
  @Id
  public Long id;

  /**
  * 是否删除
  */
  public Integer deleted = 0;

  /**
  * 公司
  */
  @Size(min = 0, max = 64, message = "公司长度不正确")
  public String company;

  /**
  * 昵称
  */
  @Size(min = 0, max = 20, message = "昵称长度不正确")
  public String nickname;

  /**
  * 用户id
  */
  @NotNull(message = "用户id不能是空")
  @Unique(table = "public.profile", column = "user_id", message = "用户id已经存在", groups = {Uni.class})
  public Long user_id;

  /**
  * 头像
  */
  @Size(min = 0, max = 42, message = "头像长度不正确")
  public String avatar;

  /**
  * 生日
  */
  public Date birthday;

  /**
  * 简介
  */
  @Size(min = 0, max = 255, message = "简介长度不正确")
  public String intro;

  /**
  * 所在城市
  */
  @Size(min = 0, max = 64, message = "所在城市长度不正确")
  public String city;

  @WhenCreated
  public Timestamp created_at = new Timestamp(System.currentTimeMillis());

  @WhenModified
  public Timestamp updated_at = new Timestamp(System.currentTimeMillis());

  public static final Finder<Long, Profile> find = new Finder<>(Profile.class);

  public interface Uni{};
}


