package com.licenseair.backend.service;

import com.google.gson.Gson;
import com.licenseair.backend.commons.model.DataResource;
import com.licenseair.backend.commons.model.IDModel;
import com.licenseair.backend.commons.model.QueryRequest;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.Language;
import com.licenseair.backend.domainModel.LanguageModel;
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
public class LanguageService extends BaseService {

  /**
   * @param AuthUser
   */
  public LanguageService(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public Language findById(Long id) {
    return Language.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public Language getWithLock(Long id) {
    return Language.find.query()
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
  public Language create(Language params) throws HttpRequestFormException {
    Language language = new Language();

    try {
      language.getClass().getField("user_id");
      Gson gson = new Gson();
      Language cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), Language.class);
      BeanUtils.copyProperties(cp, language);
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, language, this.getNotNullPropertyNames(language));
    if(validationModel(language)) {
      language.save();
    }
    return language;
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public Language update(LanguageModel params) throws HttpRequestException, HttpRequestFormException {
    Language language = this.findById(params.id);
    if(language == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    BeanUtils.copyProperties(params, language, this.getNullPropertyNames(params));
    if(validationModel(language)) {
      language.save();
    }
    return language;
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    Language language = this.findById(idModel.id);
    if(language == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }

    return language.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList<Language> where = Language.find.query().where();

    if(this.fieldExist(Language.class, "deleted")) {
      where.ne("deleted", true);
    }
    if(this.fieldExist(Language.class, "active")) {
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
    List<Language> languageList = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      languageList.add(gson.fromJson(gson.toJson(d), Language.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      languageList
    );
  }
}
