package com.licenseair.dashboard.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domainModel.UserModel;
import com.licenseair.backend.domain.Admin;
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
public class UserService extends BaseService {

  /**
   * @param AuthUser
   */
  public UserService(Admin AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public User findById(Long id) {
    return User.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public User getWithLock(Long id) {
    return User.find.query()
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
  public User create(User params) throws HttpRequestFormException {
    User user = new User();

    try {
      user.getClass().getField("user_id");
      Gson gson = new Gson();
      User cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), User.class);
      BeanUtils.copyProperties(cp, user);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, user, this.getNotNullPropertyNames(user));
    if(validationModel(user)) {
      user.save();
    }
    return user;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public User update(UserModel params) throws HttpRequestException, HttpRequestFormException {
    User user = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(user, user.id);

    BeanUtils.copyProperties(params, user, this.getNullPropertyNames(params));
    if(validationModel(user)) {
      user.save();
    }
    return user;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    User user = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(user, user.id);

    return user.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList<User> where = User.find.query().where();

    if(this.fieldExist(User.class, "deleted")) {
      where.ne("deleted", true);
    }
    if(this.fieldExist(User.class, "active")) {
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
    List<User> userList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      userList.add(gson.fromJson(gson.toJson(d), User.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      userList
    );
  }
}
