package com.licenseair.backend.controller;

import com.licenseair.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by licenseair.com
 */
@RestController
@RequestMapping("/users")
public class UsersController extends BaseController {

  /**
   * 获取用户登录信息
   * @return
   */
  @PostMapping("/get")
  public ResponseEntity<?> get() {
    UserService us = new UserService(AuthUser);
    if (AuthUser == null) {
      return ResponseEntity.ok(AuthUser);
    } else {
      return ResponseEntity.ok(us.findById(AuthUser.id));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable(value = "id") Long id) {
    // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Map<String, String> he = Map.of("s", "ss");
    //
    // String jws = Jwts.builder()
    //   .setSubject("Joe")
    //   .setHeaderParam("role", "ROLE_USER")
    //   .setExpiration(new Date(System.currentTimeMillis() + 3600000))
    //   .signWith(key).compact();
    //
    // System.out.println(jws);
    // System.out.println(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getHeader().get("Joe"));
    // System.out.println(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals("Joe"));

    // VipPlanService vps = new VipPlanService();
    // UserEntity user = UserEntity.find.byId(84L);

    UserService us = new UserService(AuthUser);
    return ResponseEntity.ok(us.findById(id));
  }

}
