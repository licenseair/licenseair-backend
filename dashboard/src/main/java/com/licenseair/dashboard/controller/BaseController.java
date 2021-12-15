package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.util.ErrorHandler;
import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domain.SessionLog;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by https://www.programschool.com/ on 17.03.2017.
 */
public class BaseController {

  // private final Logger logger = LoggerFactory.getLogger(this.getClass());
  protected static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
  protected static Admin AuthUser = null;

  @ModelAttribute
  public void getVaryRequestHeader(HttpServletRequest request) {
    String Authorization = request.getHeader("Authorization");
    if (Authorization != null && Authorization.length() > 0) {
      SessionLog sessionLog = SessionLog.find.query().where().eq("sign", Authorization).findOne();
      if (sessionLog != null) {
        byte[] key = Decoders.BASE64.decode(sessionLog.key);
        Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(sessionLog.sign);
        String account = jwt.getHeader().get("account").toString();
        Admin admin = Admin.find.query().where().eq("id", sessionLog.user_id).findOne();
        if(admin != null && (admin.mobile.equals(account) || admin.username.equals(account))) {
          AuthUser = admin;
        } else {
          AuthUser = null;
        }
      }
    } else {
      // 如果没有 Authorization 信息，删除 AuthUser
      AuthUser = null;
    }
  }

  @ModelAttribute
  public void setVaryResponseHeader(HttpServletResponse response) {
    // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Map<String, String> he = Map.of("s", "ss");
    // String jws = Jwts.builder()
    //   .setSubject("Joe")
    //   .setHeaderParam("Joe", "Joe")
    //   .setAudience("dfdsfds")
    //   .setExpiration(new Date(System.currentTimeMillis() + 3600000))
    //   .signWith(key).compact();
    // response.setHeader("token", jws);

    // System.out.println(request.getHeader("Authorization"));
    // System.out.println(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getHeader().get("Joe")
  }

}
