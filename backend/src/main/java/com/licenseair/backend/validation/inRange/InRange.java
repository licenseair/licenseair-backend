package com.licenseair.backend.validation.inRange;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by lmonkiewicz on 18.03.2017.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {InRangeValidator.class})
public @interface InRange {
  String message() default "数值超出范围";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default Integer.MIN_VALUE;

  int max() default Integer.MAX_VALUE;
}
