package com.licenseair.dashboard.library;

import com.licenseair.backend.domain.Admin;
import com.licenseair.backend.domain.SessionLog;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

public class Authorization {
  public Admin getUser(String authString) {
    Admin AuthUser = null;
    if (authString != null && authString.length() > 0) {
      SessionLog sessionLog = SessionLog.find.query().where()
        // 只允许单客户端登录
        .order().desc("id")
        .setMaxRows(1).findOne();
      if (sessionLog != null && sessionLog.sign.trim().equals(authString)) {
        byte[] key = Decoders.BASE64.decode(sessionLog.key);
        Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(sessionLog.sign);
        String account = jwt.getHeader().get("account").toString();
        Admin user = Admin.find.query().where().eq("id", sessionLog.user_id).findOne();
        if(user != null && (user.mobile != null && user.mobile.equals(account) || user.username.equals(account))) {
          AuthUser = user;
        } else {
          AuthUser = null;
        }
      }
    }
    return AuthUser;
  }
}
