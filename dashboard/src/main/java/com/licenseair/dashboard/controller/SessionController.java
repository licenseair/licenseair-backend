package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domain.Login;
import com.licenseair.backend.domain.SessionLog;
import io.ebean.Expr;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Map;

/**
 * Created by programschool.com
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {
  @PostMapping("login")
  public UserInfo login(@RequestBody Login post) throws HttpRequestException {
    Admin admin = Admin.find.query()
      .forUpdate()
      .where()
      .or(Expr.eq("username", post.account), Expr.eq("mobile", post.account))
      .findOne();

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (admin == null) {
      throw new HttpRequestException(HttpStatus.NOT_ACCEPTABLE.value(), "账号未注册");
    } else if (!encoder.matches(post.password, admin.password)) {
      throw new HttpRequestException(HttpStatus.NOT_ACCEPTABLE.value(), "密码不正确");
    }

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    String jws = Jwts.builder()
      .setSubject("sign")
      .setHeaderParams(Map.of("account", post.account))
      .signWith(key).compact();

    UserInfo userInfo = new UserInfo(post.account, jws);
    // 保存登陆记录
    this.sessionLog(admin.id, jws, key);
    return userInfo;
  }

  private void sessionLog(Long user_id, String sign, Key key) {
    SessionLog sessionLog = new SessionLog();
    sessionLog.user_id = user_id;
    sessionLog.sign = sign;
    sessionLog.key = Encoders.BASE64.encode(key.getEncoded());;
    sessionLog.save();
  }

  private class UserInfo {
    String account;
    String sign;

    public UserInfo(String account, String sign) {
      this.account = account;
      this.sign = sign;
    }
  }

}
