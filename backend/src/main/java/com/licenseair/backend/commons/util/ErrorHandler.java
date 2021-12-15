package com.licenseair.backend.commons.util;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

// @ControllerAdvice //表明这是控制器的异常处理类
@RestControllerAdvice
public class ErrorHandler {
  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  /**
   * 输入参数校验异常
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Result> handleException(HttpServletRequest req, MethodArgumentNotValidException e) {
    logger.debug("异常详情", e);
    BindingResult bindingResult = e.getBindingResult();

    //rfc4918 - 11.2. 422: Unprocessable Entity
    Result res = MiscUtil.getValidateError(bindingResult);
    return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * 404异常处理
   */
  @ExceptionHandler(value = NoHandlerFoundException.class)
  public ResponseEntity<Result> handleException(HttpServletRequest req, NoHandlerFoundException e) {
    logger.debug("异常详情", e);
    Result res = new Result(404, "页面不存在");
    return new ResponseEntity<Result>(res, HttpStatus.NOT_FOUND);
  }

  /**
   * 请求出错
   */
  @ExceptionHandler(value = HttpRequestFormException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map handleException(HttpServletRequest req, HttpRequestFormException e) {
    return Map.of(
      "error", 1,
      "code", HttpStatus.BAD_REQUEST.value(),
      "failedFields", e.getFailedFields()
    );
  }

  /**
   * 请求出错
   */
  @ExceptionHandler(value = HttpRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map handleException(HttpServletRequest req, HttpRequestException e) {
    return Map.of(
      "error", 1,
      "code", HttpStatus.BAD_REQUEST.value(),
      "message", e.getMessage()
    );
  }

  /**
   * 默认异常处理，前面未处理
   */
  @ExceptionHandler(value = Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map handleException(HttpServletRequest req, Exception e) {
    return Map.of(
      "error", 1,
      "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "message", e.getMessage()
    );
  }
}
