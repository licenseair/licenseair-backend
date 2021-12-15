package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Profile;
import com.licenseair.backend.domainModel.ProfileModel;
import com.licenseair.backend.domain.User;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

/**
* Created by licenseair.com
*/
public class ProfileService extends BaseService {

  /**
   * @param AuthUser
   */
  public ProfileService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Profile findById(Long id) {
    return Profile.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Profile getWithLock(Long id) {
    return Profile.find.query()
      .forUpdate()
      .where()
      .eq("id", id)
      .findOne();
  }

  /**
   * @param params
   * @return
   * @throws HttpRequestFormException
   */
  @Transactional
  public Profile create(Profile params) throws HttpRequestFormException {
    Profile profile = new Profile();

    try {
      profile.getClass().getField("user_id");
      Gson gson = new Gson();
      Profile cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Profile.class);
      BeanUtils.copyProperties(cp, profile);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, profile, this.getNotNullPropertyNames(profile));
    if(validationModel(profile)) {
      profile.save();
    }
    return profile;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Profile update(ProfileModel params) throws HttpRequestException, HttpRequestFormException {
    Profile profile = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(profile, profile.user_id);

    BeanUtils.copyProperties(params, profile, this.getNullPropertyNames(params));
    if(validationModel(profile)) {
      profile.save();
    }
    return profile;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Profile profile = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(profile, profile.user_id);

    return profile.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = Profile.find.query().where();

    if(this.fieldExist(Profile.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(Profile.class, "active")) {
      where.eq("active" ,1);
    }

    if(params.columns != null) {
      where.select(String.join(",", params.columns));
    }
    if(params.page != null && params.pageSize != null) {
      where.setFirstRow(params.page * params.pageSize);
      where.setMaxRows(params.pageSize);
    }

    if(params.query != null) {
      if(params.query.eq != null) {
        params.query.eq.forEach(eq -> {
          eq.forEach((String key, Object value) -> {
            where.eq(key, value);
          });
        });
      }
      if(params.query.ne != null) {
        params.query.ne.forEach(ne -> {
          ne.forEach((String key, Object value) -> {
            where.ne(key, value);
          });
        });
      }
      if(params.query.le != null) {
        params.query.le.forEach(le -> {
          le.forEach((String key, Object value) -> {
            where.le(key, value);
          });
        });
      }
      if(params.query.lt != null) {
        params.query.lt.forEach(lt -> {
          lt.forEach((String key, Object value) -> {
            where.lt(key, value);
          });
        });
      }
      if(params.query.ge != null) {
        params.query.ge.forEach(ge -> {
          ge.forEach((String key, Object value) -> {
            where.ge(key, value);
          });
        });
      }
      if(params.query.gt != null) {
        params.query.gt.forEach(gt -> {
          gt.forEach((String key, Object value) -> {
            where.gt(key, value);
          });
        });
      }
      if(params.query.arrayContains != null && params.query.arrayContains.field != null) {
        List<String> array = new ArrayList<>();
        params.query.arrayContains.array.forEach(item -> {
          array.add(item);
        });
        where.arrayContains(params.query.arrayContains.field, array.toArray());
      }
      if(params.query.order != null && params.query.order.size() > 0) {
        params.query.order.forEach((String sort) -> {
          where.order(sort);
        });
      }
    }

    Gson gson = new Gson();
    PagedList pagedList = where.findPagedList();
    List<Profile> profileList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      profileList.add(gson.fromJson(gson.toJson(d), Profile.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      profileList
    );
  }
}
