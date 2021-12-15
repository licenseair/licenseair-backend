package com.licenseair.backend.validation.isUnique;


import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {UniqueValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique{

  String message() default "已经存在";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String table();

  String column();
}
