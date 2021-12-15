package ${params.packageName}.service;

import com.google.gson.Gson;
import ${params.packageName}.commons.model.DataResource;
import ${params.packageName}.commons.model.IDModel;
import ${params.packageName}.commons.model.QueryRequest;
import ${params.packageName}.commons.util.HttpRequestException;
import ${params.packageName}.commons.util.HttpRequestFormException;
import ${params.packageName}.domain.${camelName};
import ${params.packageName}.domainModel.${camelName}Model;
import ${params.packageName}.domain.User;
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
* Created by ${params.author}
*/
public class ${camelName}Service extends BaseService {

  /**
   * @param AuthUser
   */
  public ${camelName}Service(User AuthUser) {
    super(AuthUser);
  }

  /**
   * @param id
   * @return
   */
  public ${camelName} findById(Long id) {
    return ${camelName}.find.byId(id);
  }

  /**
   * @param id
   * @return
   */
  public ${camelName} getWithLock(Long id) {
    return ${camelName}.find.query()
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
  public ${camelName} create(${camelName} params) throws HttpRequestFormException {
    ${camelName} ${camelNameVar} = new ${camelName}();

    try {
      ${camelNameVar}.getClass().getField("user_id");
      Gson gson = new Gson();
      ${camelName} cp = gson.fromJson( gson.toJson(Map.of("user_id", this.AuthUser.id)), ${camelName}.class);
      BeanUtils.copyProperties(cp, ${camelNameVar});
    } catch (NoSuchFieldException e) {
      // null
    }
    BeanUtils.copyProperties(params, ${camelNameVar}, this.getNotNullPropertyNames(${camelNameVar}));
    if(validationModel(${camelNameVar})) {
      ${camelNameVar}.save();
    }
    return ${camelNameVar};
  }

  /**
   * @param params
   * @return
   */
  @Transactional
  public ${camelName} update(${camelName}Model params) throws HttpRequestException, HttpRequestFormException {
    ${camelName} ${camelNameVar} = this.findById(params.id);
    <#if camelName == "User">
    // 检查数据和属主
    this.checkOwner(${camelNameVar}, ${camelNameVar}.id);
    <#elseif fields.user_id?exists == true>
    // 检查数据和属主
    this.checkOwner(${camelNameVar}, ${camelNameVar}.user_id);
    <#else>
    if(${camelNameVar} == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }
    </#if>

    BeanUtils.copyProperties(params, ${camelNameVar}, this.getNullPropertyNames(params));
    if(validationModel(${camelNameVar})) {
      ${camelNameVar}.save();
    }
    return ${camelNameVar};
  }

  /**
   * @param idModel
   * @return
   */
  @Transactional
  public Boolean delete(IDModel idModel) throws HttpRequestException {
    ${camelName} ${camelNameVar} = this.findById(idModel.id);
    <#if camelName == "User">
    // 检查数据和属主
    this.checkOwner(${camelNameVar}, ${camelNameVar}.id);
    <#elseif fields.user_id?exists == true>
    // 检查数据和属主
    this.checkOwner(${camelNameVar}, ${camelNameVar}.user_id);
    <#else>
    if(${camelNameVar} == null) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }
    </#if>

    return ${camelNameVar}.delete();
  }

  /**
   * 查询数据
   * @param params
   * @return
   */
  public DataResource query(QueryRequest params) {
    ExpressionList where = ${camelName}.find.query().where();

    if(this.fieldExist(${camelName}.class, "deleted")) {
      where.ne("deleted" ,1);
    }
    if(this.fieldExist(${camelName}.class, "active")) {
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
    List<${camelName}> ${camelNameVar}List = new ArrayList<>();
    pagedList.getList().forEach(d -> {
      ${camelNameVar}List.add(gson.fromJson(gson.toJson(d), ${camelName}.class));
    });
    return new DataResource(
      pagedList.getTotalPageCount(),
      pagedList.getTotalCount(),
      pagedList.getPageIndex(),
      pagedList.getPageSize(),
      ${camelNameVar}List
    );
  }
}
