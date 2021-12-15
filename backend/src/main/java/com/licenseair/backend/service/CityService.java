package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.City;
import com.licenseair.backend.domainModel.CityModel;
import com.licenseair.backend.domain.User;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by foxsir
*/
public class CityService extends BaseService {

  /**
   * @param AuthUser
   */
  public CityService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public City findById(Long id) {
    return City.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public City getWithLock(Long id) {
    return City.find.query()
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
  public City create(City params) throws HttpRequestFormException {
    City city = new City();

    try {
      city.getClass().getField("user_id");
      Gson gson = new Gson();
      City cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), City.class);
      BeanUtils.copyProperties(cp, city);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, city, this.getNotNullPropertyNames(city));
    if(validationModel(city)) {
      city.save();
    }
    return city;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public City update(CityModel params) throws HttpRequestException, HttpRequestFormException {
    City city = this.findById(params.id);
    if(city == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    BeanUtils.copyProperties(params, city, this.getNullPropertyNames(params));
    if(validationModel(city)) {
      city.save();
    }
    return city;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    City city = this.findById(idModel.id);
    if(city == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    return city.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = City.find.query().where();

    if(this.fieldExist(City.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(City.class, "active")) {
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
    List<City> cityList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      cityList.add(gson.fromJson(gson.toJson(d), City.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      cityList
    );
  }
}
