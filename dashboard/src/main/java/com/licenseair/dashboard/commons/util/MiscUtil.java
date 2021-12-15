package com.licenseair.dashboard.commons.util;

import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class MiscUtil {

  @SuppressWarnings("unused")
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(MiscUtil.class);

  static public Result getValidateError(BindingResult bindingResult) {

    if (bindingResult.hasErrors() == false) {
      return null;
    }

    Map<String, String> fieldErrors = new HashMap<String, String>();

    for (FieldError error : bindingResult.getFieldErrors()) {
      // error.getCode()
      fieldErrors.put(error.getField(), error.getDefaultMessage());
    }

    Result ret = new Result(422, "输入错误"); //rfc4918 - 11.2. 422: Unprocessable Entity
    ret.putData("fieldErrors", fieldErrors);

    return ret;
  }

  public static String toHexString(byte[] array) {
    String str = new String(array);
    return str;
  }

  public static byte[] toByteArray(String s) {
    String[] byteValues = s.substring(1, s.length() - 1).split(",");
    byte[] bytes = new byte[byteValues.length];
    for (int i = 0, len = byteValues.length; i < len; i++) {
      bytes[i] = Byte.parseByte(byteValues[i].trim());
    }
    return bytes;
  }
}
