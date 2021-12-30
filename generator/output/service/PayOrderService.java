package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.PayOrder;
import com.licenseair.backend.domainModel.PayOrderModel;
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
public class PayOrderService extends BaseService {

  /**
   * @param AuthUser
   */
  public PayOrderService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public PayOrder findById(Long id) {
    return PayOrder.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public PayOrder getWithLock(Long id) {
    return PayOrder.find.query()
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
  public PayOrder create(PayOrder params) throws HttpRequestFormException {
    PayOrder payOrder = new PayOrder();

    try {
      payOrder.getClass().getField("user_id");
      Gson gson = new Gson();
      PayOrder cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), PayOrder.class);
      BeanUtils.copyProperties(cp, payOrder);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, payOrder, this.getNotNullPropertyNames(payOrder));
    if(validationModel(payOrder)) {
      payOrder.save();
    }
    return payOrder;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public PayOrder update(PayOrderModel params) throws HttpRequestException, HttpRequestFormException {
    PayOrder payOrder = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(payOrder, payOrder.user_id);

    BeanUtils.copyProperties(params, payOrder, this.getNullPropertyNames(params));
    if(validationModel(payOrder)) {
      payOrder.save();
    }
    return payOrder;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    PayOrder payOrder = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(payOrder, payOrder.user_id);

    return payOrder.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList<PayOrder> where = PayOrder.find.query().where();

    if(this.fieldExist(PayOrder.class, "deleted")) {
      where.ne("deleted", true);
    }
    if(this.fieldExist(PayOrder.class, "active")) {
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
    List<PayOrder> payOrderList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      payOrderList.add(gson.fromJson(gson.toJson(d), PayOrder.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      payOrderList
    );
  }
}
