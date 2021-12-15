package com.licenseair.backend.service;

import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.config.SiteConfig;
import com.licenseair.backend.domain.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import java.lang.reflect.Field;
import java.util.*;

class BaseService {
  protected User AuthUser;

  @Value("${spring.application.name}")
  private String AppName;

  // 禁止从前台修改的字段
  private final List<String> NotAllowedField = List.of(
    "id",
    "uuid",
    "deleted",
    "created_at",
    "updated_at",
    "is_pay",
    "pay_time",
    "vip_plan_id",
    "vip_expired",
    "active",
    "domain"
  );

  /**
   * @param AuthUser
   */
  public BaseService(User AuthUser) {
    this.AuthUser = AuthUser;
  }

  /**
   * 返回null值字段
   * @param source
   * @return
   */
  protected String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    List<String> emptyNames = new ArrayList<>();
    for(java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }

    // 设置禁止从前台修改的字段
    emptyNames.addAll(this.NotAllowedField);

    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  /**
   * 返回有预设定值的字段
   * @param source
   * @return
   */
  protected String[] getNotNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    List<String> emptyNames = new ArrayList<>();
    for(java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (
        srcValue != null && // 忽略非null值
        !srcValue.getClass().equals(ArrayList.class) // 忽略非ArrayList值
      ) {
        emptyNames.add(pd.getName());
      }
    }

    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  /**
   * 验证输入
   * @param object
   * @return
   * @throws HttpRequestFormException
   */
  protected Boolean validationModel(@Validated() Object object) throws HttpRequestFormException {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
    if (constraintViolations.isEmpty() == false) {
      Map<String, String> messages = new HashMap<>();
      constraintViolations.stream().forEach(e -> {
        messages.put(e.getPropertyPath().toString(), e.getMessage());
      });
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), messages);
    }
    return true;
  }

  /**
   * 检查字段是否存在
   * @param model
   * @param field
   * @return
   */
  protected Boolean fieldExist(Class model, String field) {
    try {
      Field exist = model.getDeclaredField(field);
    } catch (Exception e){
      return false;
    }
    return true;
  }

  /**
   * 检查数据和属主
   * @param model
   * @param user_id
   * @throws HttpRequestException
   */
  protected void checkOwner(Object model, Long user_id) throws HttpRequestException {
    boolean check = user_id.equals(AuthUser.id);
    if(model == null || !check && !SiteConfig.AppName.equals("dashboard")) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "数据不存在");
    }
  }

}
