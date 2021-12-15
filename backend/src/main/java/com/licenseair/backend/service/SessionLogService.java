package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.SessionLog;
import com.licenseair.backend.domainModel.SessionLogModel;
import com.licenseair.backend.domain.User;
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
public class SessionLogService extends BaseService {

  /**
   * @param AuthUser
   */
  public SessionLogService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public SessionLog findById(Long id) {
    return SessionLog.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public SessionLog getWithLock(Long id) {
    return SessionLog.find.query()
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
  public SessionLog create(SessionLog params) throws HttpRequestFormException {
    SessionLog sessionLog = new SessionLog();

    try {
      sessionLog.getClass().getField("user_id");
      Gson gson = new Gson();
      SessionLog cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), SessionLog.class);
      BeanUtils.copyProperties(cp, sessionLog);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, sessionLog, this.getNotNullPropertyNames(sessionLog));
    if(validationModel(sessionLog)) {
      sessionLog.save();
    }
    return sessionLog;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public SessionLog update(SessionLogModel params) throws HttpRequestException, HttpRequestFormException {
    SessionLog sessionLog = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(sessionLog, sessionLog.user_id);

    BeanUtils.copyProperties(params, sessionLog, this.getNullPropertyNames(params));
    if(validationModel(sessionLog)) {
      sessionLog.save();
    }
    return sessionLog;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    SessionLog sessionLog = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(sessionLog, sessionLog.user_id);

    return sessionLog.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = SessionLog.find.query().where();

    if(this.fieldExist(SessionLog.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(SessionLog.class, "active")) {
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
    List<SessionLog> sessionLogList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      sessionLogList.add(gson.fromJson(gson.toJson(d), SessionLog.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      sessionLogList
    );
  }
}
