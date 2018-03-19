package com.jhon.rain.web.service;

import com.jhon.rain.biz.service.HouseService;
import com.jhon.rain.common.model.HouseDO;
import com.jhon.rain.common.page.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>功能描述</br>房产的业务逻辑接口测试类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 15:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseSeviceTest {

  @Autowired
  private HouseService houseService;

  @Test
  public void queryHousePageDataTest() {
    String name = "";
    Integer type = 1;
    Integer pageNum = 1;
    Integer pageSize = 10;
    PageData<HouseDO> pageData = houseService.queryHouse(name, type, pageNum, pageSize);
    System.out.println(pageData.getList());
  }
}
