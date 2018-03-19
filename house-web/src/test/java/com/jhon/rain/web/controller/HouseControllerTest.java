package com.jhon.rain.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 16:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseControllerTest {

  /**
   * 模拟mvc测试对象
   */
  private MockMvc mockMvc;

  /**
   * web项目上下文
   */
  @Autowired
  private WebApplicationContext webApplicationContext;

  /**
   * <pre>所有方法执行之前都调用改方法</pre>
   */
  @Before
  public void setup() {
    /** 获取mockmvc对象实例 **/
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testHouseList() throws Exception {
    MvcResult mvcResult = mockMvc
            .perform(
                    MockMvcRequestBuilders
                            .post("/house/list")
                            .param("pageNum", "1")
                            .param("pageSize", "10")
                            .param("name", "")
                            .param("type", "1")
            )
            .andReturn();

    /** 获取响应的状态码 **/
    int status = mvcResult.getResponse().getStatus();
    /** 获取返回的数据结果 **/
    String result = mvcResult.getResponse().getContentAsString();
    /** 数据的判断 **/
    Assert.assertEquals("请求错误", 200, status);
    Assert.assertEquals("返回结果不一致", "", result);
    System.out.println();
  }
}
