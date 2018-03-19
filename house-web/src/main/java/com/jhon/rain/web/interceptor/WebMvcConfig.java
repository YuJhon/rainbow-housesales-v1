package com.jhon.rain.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 20:07
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private AuthInterceptor authInterceptor;

  @Autowired
  private AuthActionInterceptor authActionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    /** 首先获取session中的用户信息，存放到线程局部变量ThreadLocal中 **/
    registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
    /** 拦截对应的请求 **/
    registry.addInterceptor(authActionInterceptor)
            .addPathPatterns("/house/add")
            .addPathPatterns("/accounts/profile")
            .addPathPatterns("/accounts/profileSubmit")
            .addPathPatterns("/house/bookmarked")
            .addPathPatterns("/house/del")
            .addPathPatterns("/house/ownlist")
            .addPathPatterns("/house/toadd");
    super.addInterceptors(registry);
  }
}
