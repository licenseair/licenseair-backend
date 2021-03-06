package com.licenseair.backend.commons.auth;

import org.slf4j.LoggerFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthError implements AuthenticationEntryPoint, AccessDeniedHandler {

  @SuppressWarnings("unused")
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(JwtAuthError.class);

  // 认证失败处理，返回401 json数据
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write("{\"status\":401,\"message\":\"Unauthorized or invalid token\"}");
  }

  // 鉴权失败处理，返回403 json数据
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write("{\"status\":403,\"message\":\"Forbidden\"}");
  }
}
