package com.licenseair.backend.controller;

import com.licenseair.backend.commons.util.ErrorHandler;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.Security;
import com.licenseair.backend.domain.SmsLog;
import com.licenseair.backend.domain.User;
import com.licenseair.backend.library.Authorization;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by https://www.programschool.com/ on 17.03.2017.
 */
public class BaseController {

  protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
  protected static User AuthUser = null;

  @ModelAttribute
  public void getVaryRequestHeader(HttpServletRequest request) {
    AuthUser = new Authorization().getUser(request.getHeader("Authorization"));
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

  /**
   * 检查验证码
   * @param post
   * @throws HttpRequestException
   */
  protected void checkSmsTime(Security post) throws HttpRequestException {
    Timestamp m2 = new Timestamp(System.currentTimeMillis() - 1000*60*10);
    List<SmsLog> check = SmsLog.find.query().where()
      .eq("mobile", post.mobile)
      .eq("code", post.code)
      .gt("created_at", m2)
      .orderBy("id DESC")
      .findList();

    if(check.stream().count() == 0) {
      throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "验证码不正确，或已经过期");
    }
  }

}
