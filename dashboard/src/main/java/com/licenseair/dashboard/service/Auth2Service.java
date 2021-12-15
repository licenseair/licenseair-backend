package com.licenseair.dashboard.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Auth2;
import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domainModel.Auth2Model;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by foxsir
*/
public class Auth2Service extends BaseService {

  /**
   * @param AuthUser
   */
  public Auth2Service(Admin AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Auth2 findById(Long id) {
    return Auth2.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Auth2 getWithLock(Long id) {
    return Auth2.find.query()
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
  public Auth2 create(Auth2 params) throws HttpRequestFormException {
    Auth2 auth2 = new Auth2();

    try {
      auth2.getClass().getField("user_id");
      Gson gson = new Gson();
      Auth2 cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Auth2.class);
      BeanUtils.copyProperties(cp, auth2);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, auth2, this.getNotNullPropertyNames(auth2));
    if(validationModel(auth2)) {
      auth2.save();
    }
    return auth2;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Auth2 update(Auth2Model params) throws HttpRequestException, HttpRequestFormException {
    Auth2 auth2 = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(auth2, auth2.user_id);

    BeanUtils.copyProperties(params, auth2, this.getNullPropertyNames(params));
    if(validationModel(auth2)) {
      auth2.save();
    }
    return auth2;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Auth2 auth2 = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(auth2, auth2.user_id);

    return auth2.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = Auth2.find.query().where();

    if(this.fieldExist(Auth2.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(Auth2.class, "active")) {
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
    List<Auth2> auth2List = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      auth2List.add(gson.fromJson(gson.toJson(d), Auth2.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      auth2List
    );
  }
}
