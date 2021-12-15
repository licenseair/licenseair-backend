package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domainModel.AdminModel;
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
public class AdminService extends BaseService {

  /**
   * @param AuthUser
   */
  public AdminService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Admin findById(Long id) {
    return Admin.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Admin getWithLock(Long id) {
    return Admin.find.query()
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
  public Admin create(Admin params) throws HttpRequestFormException {
    Admin admin = new Admin();

    try {
      admin.getClass().getField("user_id");
      Gson gson = new Gson();
      Admin cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Admin.class);
      BeanUtils.copyProperties(cp, admin);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, admin, this.getNotNullPropertyNames(admin));
    if(validationModel(admin)) {
      admin.save();
    }
    return admin;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Admin update(AdminModel params) throws HttpRequestException, HttpRequestFormException {
    Admin admin = this.findById(params.id);
    if(admin == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    BeanUtils.copyProperties(params, admin, this.getNullPropertyNames(params));
    if(validationModel(admin)) {
      admin.save();
    }
    return admin;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Admin admin = this.findById(idModel.id);
    if(admin == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    return admin.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = Admin.find.query().where();

    if(this.fieldExist(Admin.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(Admin.class, "active")) {
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
    List<Admin> adminList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      adminList.add(gson.fromJson(gson.toJson(d), Admin.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      adminList
    );
  }
}
