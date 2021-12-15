package com.licenseair.dashboard.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.domainModel.AppInstanceModel;
import com.licenseair.backend.domain.Admin;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by licenseair.com
*/
public class AppInstanceService extends BaseService {

  /**
   * @param AuthUser
   */
  public AppInstanceService(Admin AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public AppInstance findById(Long id) {
    return AppInstance.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public AppInstance getWithLock(Long id) {
    return AppInstance.find.query()
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
  public AppInstance create(AppInstance params) throws HttpRequestFormException {
    AppInstance appInstance = new AppInstance();

    try {
      appInstance.getClass().getField("user_id");
      Gson gson = new Gson();
      AppInstance cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), AppInstance.class);
      BeanUtils.copyProperties(cp, appInstance);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, appInstance, this.getNotNullPropertyNames(appInstance));
    if(validationModel(appInstance)) {
      appInstance.save();
    }
    return appInstance;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public AppInstance update(AppInstanceModel params) throws HttpRequestException, HttpRequestFormException {
    AppInstance appInstance = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(appInstance, appInstance.user_id);

    BeanUtils.copyProperties(params, appInstance, this.getNullPropertyNames(params));
    if(validationModel(appInstance)) {
      appInstance.save();
    }
    return appInstance;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    AppInstance appInstance = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(appInstance, appInstance.user_id);

    return appInstance.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = AppInstance.find.query().where();

    if(this.fieldExist(AppInstance.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(AppInstance.class, "active")) {
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
    List<AppInstance> appInstanceList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      appInstanceList.add(gson.fromJson(gson.toJson(d), AppInstance.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      appInstanceList
    );
  }
}
