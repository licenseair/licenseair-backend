package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Category;
import com.licenseair.backend.domainModel.CategoryModel;
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
public class CategoryService extends BaseService {

  /**
   * @param AuthUser
   */
  public CategoryService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Category findById(Long id) {
    return Category.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Category getWithLock(Long id) {
    return Category.find.query()
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
  public Category create(Category params) throws HttpRequestFormException {
    Category category = new Category();

    try {
      category.getClass().getField("user_id");
      Gson gson = new Gson();
      Category cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Category.class);
      BeanUtils.copyProperties(cp, category);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, category, this.getNotNullPropertyNames(category));
    if(validationModel(category)) {
      category.save();
    }
    return category;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Category update(CategoryModel params) throws HttpRequestException, HttpRequestFormException {
    Category category = this.findById(params.id);
    if(category == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    BeanUtils.copyProperties(params, category, this.getNullPropertyNames(params));
    if(validationModel(category)) {
      category.save();
    }
    return category;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Category category = this.findById(idModel.id);
    if(category == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    return category.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList<Category> where = Category.find.query().where();

    if(this.fieldExist(Category.class, "deleted")) {
      where.ne("deleted", true);
    }
    if(this.fieldExist(Category.class, "active")) {
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
    List<Category> categoryList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      categoryList.add(gson.fromJson(gson.toJson(d), Category.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      categoryList
    );
  }
}
