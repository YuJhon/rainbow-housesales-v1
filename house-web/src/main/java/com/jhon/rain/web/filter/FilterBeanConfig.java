package com.jhon.rain.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/6 23:57
 */
@Configuration
public class FilterBeanConfig {

  @Bean(name = "logFilter")
  public Filter logFilterInit() {
    return new LogFilter();
  }

  @Bean
  public FilterRegistrationBean logFilter(){
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(logFilterInit());
    registration.addUrlPatterns("/*");
    registration.setName("logFilter");
    return registration;
  }
}
