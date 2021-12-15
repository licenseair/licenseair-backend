package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Platform;
import com.licenseair.backend.domain.User;
import com.licenseair.backend.domainModel.PlatformModel;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by licenseair.com
*/
public class PlatformService extends BaseService {

  /**
   * @param AuthUser
   */
  public PlatformService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Platform findById(Long id) {
    return Platform.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Platform getWithLock(Long id) {
    return Platform.find.query()
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
  public Platform create(Platform params) throws HttpRequestFormException {
    Platform platform = new Platform();

    try {
      platform.getClass().getField("user_id");
      Gson gson = new Gson();
      Platform cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Platform.class);
      BeanUtils.copyProperties(cp, platform);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, platform, this.getNotNullPropertyNames(platform));
    if(validationModel(platform)) {
      platform.save();
    }
    return platform;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Platform update(PlatformModel params) throws HttpRequestException, HttpRequestFormException {
    Platform platform = this.findById(params.id);
    if(platform == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    BeanUtils.copyProperties(params, platform, this.getNullPropertyNames(params));
    if(validationModel(platform)) {
      platform.save();
    }
    return platform;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Platform platform = this.findById(idModel.id);
    if(platform == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    return platform.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = Platform.find.query().where();

    if(this.fieldExist(Platform.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(Platform.class, "active")) {
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
    List<Platform> platformList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      platformList.add(gson.fromJson(gson.toJson(d), Platform.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      platformList
    );
  }
}
