package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Application;
import com.licenseair.backend.domainModel.ApplicationModel;
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
public class ApplicationService extends BaseService {

  /**
   * @param AuthUser
   */
  public ApplicationService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Application findById(Long id) {
    return Application.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Application getWithLock(Long id) {
    return Application.find.query()
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
  public Application create(Application params) throws HttpRequestFormException {
    Application application = new Application();

    try {
      application.getClass().getField("user_id");
      Gson gson = new Gson();
      Application cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Application.class);
      BeanUtils.copyProperties(cp, application);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, application, this.getNotNullPropertyNames(application));
    if(validationModel(application)) {
      application.save();
    }
    return application;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Application update(ApplicationModel params) throws HttpRequestException, HttpRequestFormException {
    Application application = this.findById(params.id);
    if(application == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    BeanUtils.copyProperties(params, application, this.getNullPropertyNames(params));
    if(validationModel(application)) {
      application.save();
    }
    return application;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Application application = this.findById(idModel.id);
    if(application == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    return application.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList<Application> where = Application.find.query().where();

    if(this.fieldExist(Application.class, "deleted")) {
      where.ne("deleted", true);
    }
    if(this.fieldExist(Application.class, "active")) {
      where.eq("active", true);
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
      if(params.query.idIn != null) {
        List<Integer> Ids = new ArrayList<>();
        params.query.idIn.forEach(item -> {
          Ids.add(item);
        });
        where.idIn(Ids);
      }
      if(params.query.order != null && params.query.order.size() > 0) {
        params.query.order.forEach((String sort) -> {
          where.order(sort);
        });
      }
    }

    Gson gson = new Gson();
    PagedList pagedList = where.findPagedList();
    List<Application> applicationList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      applicationList.add(gson.fromJson(gson.toJson(d), Application.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      applicationList
    );
  }
}
