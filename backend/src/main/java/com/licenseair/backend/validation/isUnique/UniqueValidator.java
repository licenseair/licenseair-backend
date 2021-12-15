package com.licenseair.backend.validation.isUnique;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.ebean.DB;
import io.ebean.SqlRow;
import org.springframework.stereotype.Component;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, String> {

  private String table;
  private String column;

  @Override
  public void initialize(Unique constraintAnnotation) {
    table = constraintAnnotation.table();
    column = constraintAnnotation.column();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String sql = String.format(
      "select %s from %s where %s = :%s",
      column, table, column, column
    );

    SqlRow row = DB.sqlQuery(sql)
      .setParameter(column, value)
      .findOne();

    return row == null;
  }

}
