package com.licenseair.backend.controller;

import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.Security;
import com.licenseair.backend.domain.User;
import com.licenseair.backend.commons.model.UpdateResponse;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;


@RestController
@RequestMapping("/security")
public class SecurityController extends BaseController {

  @PostMapping(value = "/mobile")
  public UpdateResponse mobile(@RequestBody @Validated(User.Uni.class) Security post) throws HttpRequestException {
    this.checkSmsTime(post);

    User user = User.find.query().where()
      .eq("mobile", post.mobile)
      .findOne();

    if (user.mobile.equals(post.mobile)) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "手机号未修改。");
    } else {
      user.setMobile(post.mobile);
      user.save();
    }

    return new UpdateResponse(user);
  }

  @PostMapping(value = "/password")
  public UpdateResponse password(@RequestBody Security post) throws HttpRequestException {
    this.checkSmsTime(post);

    User user = User.find.query().where()
      .eq("mobile", post.mobile)
      .findOne();

    if (user == null || !post.mobile.equals(user.mobile)) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "请使用已经注册过的手机号。");
    } else {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      user.setPassword(encoder.encode(post.password));
      user.save();
    }

    UserReadOnly userReadOnly = new UserReadOnly();
    BeanUtils.copyProperties(user, userReadOnly);
    return new UpdateResponse(userReadOnly);
  }

}

