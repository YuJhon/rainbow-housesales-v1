package com.jhon.rain.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/13 9:32
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {Exception.class, RuntimeException.class})
  private String error500(HttpServletRequest request, Exception e) {
    log.error(e.getMessage(), e);
    log.error(request.getRequestURL() + " encounter 500");
    return "error/500";
  }
}
