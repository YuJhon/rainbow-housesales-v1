package com.jhon.rain.web.service;

import com.jhon.rain.biz.service.UserService;
import com.jhon.rain.common.model.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>功能描述</br>单元测试类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 9:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTests {

  @Autowired
  private UserService userService;

  @Test
  public void userInfoTest() {
    UserDO user = userService.auth("jiangy19@126.com", "1111");
    assert user != null;
    System.out.println(user.getAboutme());
  }
}
