package com.licenseair.dashboard.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Wallet;
import com.licenseair.backend.domainModel.WalletModel;
import com.licenseair.backend.domain.Admin;
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
public class WalletService extends BaseService {

  /**
   * @param AuthUser
   */
  public WalletService(Admin AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Wallet findById(Long id) {
    return Wallet.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Wallet getWithLock(Long id) {
    return Wallet.find.query()
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
  public Wallet create(Wallet params) throws HttpRequestFormException {
    Wallet wallet = new Wallet();

    try {
      wallet.getClass().getField("user_id");
      Gson gson = new Gson();
      Wallet cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Wallet.class);
      BeanUtils.copyProperties(cp, wallet);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, wallet, this.getNotNullPropertyNames(wallet));
    if(validationModel(wallet)) {
      wallet.save();
    }
    return wallet;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Wallet update(WalletModel params) throws HttpRequestException, HttpRequestFormException {
    Wallet wallet = this.findById(params.id);
    // 检查数据和属主
    this.checkOwner(wallet, wallet.user_id);

    BeanUtils.copyProperties(params, wallet, this.getNullPropertyNames(params));
    if(validationModel(wallet)) {
      wallet.save();
    }
    return wallet;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Wallet wallet = this.findById(idModel.id);
    // 检查数据和属主
    this.checkOwner(wallet, wallet.user_id);

    return wallet.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList<Wallet> where = Wallet.find.query().where();

    if(this.fieldExist(Wallet.class, "deleted")) {
      where.ne("deleted", true);
    }
    if(this.fieldExist(Wallet.class, "active")) {
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
    List<Wallet> walletList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      walletList.add(gson.fromJson(gson.toJson(d), Wallet.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      walletList
    );
  }
}
