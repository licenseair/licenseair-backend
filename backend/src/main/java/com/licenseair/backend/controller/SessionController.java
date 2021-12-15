package com.licenseair.backend.controller;

import com.licenseair.backend.commons.model.CreateResponse;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.commons.util.HttpRequestFormException;
import com.licenseair.backend.domain.*;
import com.licenseair.backend.domain.*;
// import com.licenseair.backend.library.Slug;
import com.licenseair.backend.service.UserService;
import io.ebean.Expr;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {
  @Value("${site.url}")
  private String siteUrl;

  @PostMapping("login")
  public UserInfo login(@RequestBody Login post) throws HttpRequestException {
    User user = User.find.query()
      .forUpdate()
      .where()
      .or(Expr.eq("username", post.account), Expr.eq("mobile", post.account))
      .findOne();

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (user == null) {
      throw new HttpRequestException(HttpStatus.NOT_ACCEPTABLE.value(), "账号未注册");
    } else if (!encoder.matches(post.password, user.password)) {
      throw new HttpRequestException(HttpStatus.NOT_ACCEPTABLE.value(), "密码不正确");
    }

    return getUserInfo(user);
  }

  // @PostMapping("get-wechat-url")
  // private String wechat() {
  //   AuthRequest authRequest = getAuthRequestForWechat();
  //   String authorizeUrl = null;
  //
  //   if (AuthUser != null && AuthUser.domain != null) {
  //     authorizeUrl = authRequest.authorize(AuthUser.domain);
  //   } else {
  //     authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
  //   }
  //
  //   return authorizeUrl;
  // }
  //
  // // @GetMapping("login-for-wechat")
  // @Transactional
  // public String loginForWechat(AuthCallback callback, @RequestParam("state") String state) {
  //   AuthRequest authRequest = getAuthRequestForWechat();
  //   AuthResponse authResponse = authRequest.login(callback);
  //   Gson gson = new Gson();
  //   if(authResponse.ok()) {
  //     String json = gson.toJson(authResponse.getData());
  //     Map data = gson.fromJson(json, Map.class);
  //     Map rawUserInfo = gson.fromJson(gson.toJson(data.get("rawUserInfo")), Map.class);
  //
  //     String sign = "false";
  //
  //     Auth2 auth2 = Auth2.find.query().where()
  //       .setMaxRows(1)
  //       .eq("uuid", data.get("uuid").toString())
  //       .eq("source", data.get("source").toString())
  //       .findOne();
  //     if (auth2 != null) {
  //       User alreadyUser = User.find.byId(auth2.user_id);
  //       if (alreadyUser != null) {
  //         sign = getUserInfo(alreadyUser).sign;
  //       }
  //     } else {
  //       auth2 = new Auth2();
  //       auth2.setUuid(data.get("uuid").toString());
  //       if(rawUserInfo.get("unionid") != null) {
  //         auth2.setUnionid(rawUserInfo.get("unionid").toString());
  //       }
  //       auth2.setSource(data.get("source").toString());
  //       auth2.setRaw_data(json);
  //
  //       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  //
  //       User newUser = User.find.query().where()
  //         .eq("domain", state).findOne();
  //       if (newUser != null) {
  //         newUser = new User();
  //         newUser.username = getUsername(data.get("username").toString(), 0);
  //         // newUser.mobile = null;
  //         newUser.password = encoder.encode(data.get("uuid").toString());
  //         newUser.email = null;
  //         newUser.domain = getDomain();
  //         newUser.save();
  //       }
  //
  //       auth2.setUser_id(newUser.id);
  //       auth2.save();
  //       sign = getUserInfo(newUser).sign;
  //     }
  //
  //     return String.format("<script>top.location.href = '%s/apply-auth/%s';</script>", this.siteUrl, sign);
  //   } else {
  //     return String.format("<script>top.location.href = '%s/apply-auth/%s';</script>", this.siteUrl, "false");
  //   }
  // }

  private UserInfo getUserInfo(User user) {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    String jws = Jwts.builder()
      .setSubject("sign")
      .setHeaderParams(Map.of("account", user.username))
      .signWith(key, SignatureAlgorithm.HS256).compact();

    UserInfo userInfo = new UserInfo(user.username, jws);
    // 保存登陆记录
    this.sessionLog(user.id, jws, key);
    return userInfo;
  }

  // private String getUsername(String username, int suffix) {
  //   String name = Slug.get(username);
  //   int count = User.find.query().where()
  //     .eq("username", name)
  //     .findCount();
  //
  //   if(count > 0) {
  //     int s = suffix + 1;
  //     String newName = String.format("%s%s", name, s);
  //     return getUsername(newName, s);
  //   }
  //
  //   return name;
  // }

  // private AuthRequest getAuthRequestForWechat() {
  //   return new AuthWeChatOpenRequest(AuthConfig.builder()
  //     .clientId("wxd2bb3f354d7bbf2a")
  //     .clientSecret("123d9a2ff3f2109b91b4913d18a806aa")
  //     .redirectUri("http://data.programschool.com/session/login-for-wechat")
  //     .build());
  // }
  //
  // @PostMapping("login-for-sms")
  // public UserInfo loginForSms(@RequestBody LoginForSms post) throws HttpRequestFormException, HttpRequestException {
  //   User user = User.find.query()
  //     .forUpdate()
  //     .where()
  //     .eq("mobile", post.mobile)
  //     .findOne();
  //
  //   if (user == null) {
  //     throw new HttpRequestException(HttpStatus.NOT_ACCEPTABLE.value(), "账号未注册");
  //   }
  //
  //   // 检查验证码
  //   Security security = new Security();
  //   security.mobile = post.mobile;
  //   security.code = post.code;
  //   this.checkSmsTime(security);
  //   return getUserInfo(user);
  // }

  private void sessionLog(Long user_id, String sign, Key key) {
    SessionLog sessionLog = new SessionLog();
    sessionLog.user_id = user_id;
    sessionLog.sign = sign;
    sessionLog.key = Encoders.BASE64.encode(key.getEncoded());;
    sessionLog.save();
  }

  @PostMapping("signup")
  public CreateResponse signup(@RequestBody @Validated(User.Uni.class) Signup post) throws HttpRequestFormException, HttpRequestException {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    Security security = new Security();
    security.mobile = post.mobile;
    security.code = post.code;
    this.checkSmsTime(security);

    User user = new User();
    String regex = "^[a-z0-9\\._-]{3,20}$";
    Pattern pattern = Pattern.compile(regex);
    if (!pattern.matcher(post.username.trim()).matches()) {
      throw new HttpRequestException(HttpStatus.NOT_ACCEPTABLE.value(), "用户名只能是 (小写字母,数字,-,_) 组成，3-20位");
    }
    user.username = post.username.trim();
    user.mobile = post.mobile;
    user.password = encoder.encode(post.password);
    user.email = post.email;
    user.domain = getDomain();

    try {
      UserService userService = new UserService(user);
      return new CreateResponse(userService.create(user));
    } catch (HttpRequestFormException e) {
      throw new HttpRequestFormException(HttpStatus.BAD_REQUEST.value(), e.getFailedFields());
    }
  }

  private String getDomain() {
    String domain = UUID.randomUUID().toString().replaceAll("-","").substring(0, 14);
    int check = User.find.query().where()
      .eq("domain", domain)
      .findCount();

    if(check > 0) {
      return getDomain();
    }
    return domain;
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
