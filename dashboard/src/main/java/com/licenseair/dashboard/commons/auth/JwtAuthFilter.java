package com.licenseair.dashboard.commons.auth;

import com.licenseair.backend.domain.Admin;
import com.licenseair.dashboard.library.Authorization;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

  // @Autowired
  // private JwtUtil jwtUtil;
  // private String tokenPrefix = "Bearer";

  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain) throws ServletException, IOException {
    // 从http头部读取jwt
    String authHeader = request.getHeader("Authorization");

    // 忽略掉 OPTIONS 请求
    if ("OPTIONS".equals(request.getMethod())) {
      this.setAuthInfo(request, "OPTIONS", "OPTIONS");
    }

    if (authHeader != null && authHeader.length() > 0) {
      String username = null, role = null;

      Admin AuthUser = new Authorization().getUser(authHeader);
      if(AuthUser != null) {
        username = AuthUser.username;
        role = "USER";
      } else {
        username = null;
      }

      // 如果jwt正确解出账号信息，说明是合法用户，设置认证信息，认证通过
      this.setAuthInfo(request, username, role);
    }

    // 调用下一个过滤器
    chain.doFilter(request, response);
  }


  private void setAuthInfo(HttpServletRequest request, String username, String role) {
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority(role));
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        username, null, authorities
      );

      // 把请求的信息设置到UsernamePasswordAuthenticationToken details对象里面，包括发请求的ip等
      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // 设置认证信息
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
  }
}
