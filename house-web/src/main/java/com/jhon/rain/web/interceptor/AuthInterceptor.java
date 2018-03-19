package com.jhon.rain.web.interceptor;

import com.google.common.base.Joiner;
import com.jhon.rain.common.constants.CommonConstants;
import com.jhon.rain.common.model.UserDO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>功能描述</br>拦截授权</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 20:05
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o) throws Exception {
    Map<String, String[]> map = httpServletRequest.getParameterMap();
    map.forEach((k, v) -> {
      if ("successMsg".equals(k) || "errorMsg".equals(k) || "target".equals(k)) {
        httpServletRequest.setAttribute(k, Joiner.on(",").join(v));
      }
    });
    String requestUri = httpServletRequest.getRequestURI();
    if (requestUri.startsWith("/static") || requestUri.startsWith("/error")) {
      return true;
    }
    HttpSession session = httpServletRequest.getSession(true);
    UserDO user = (UserDO) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
    if (user != null) {
      /** 将当前登陆的用户信息放到线程局部变量中:方便在同一个线程中使用 **/
      UserContext.setUser(user);
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         Object o,
                         ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse,
                              Object o, Exception e) throws Exception {
    /** 移除数据 **/
    UserContext.remove();
  }
}
