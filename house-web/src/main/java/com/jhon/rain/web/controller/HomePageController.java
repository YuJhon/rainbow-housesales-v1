package com.jhon.rain.web.controller;

import com.jhon.rain.biz.service.RecommendService;
import com.jhon.rain.common.constants.CommonConstants;
import com.jhon.rain.common.model.HouseDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>功能描述</br>首页控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 23:51
 */
@Controller
public class HomePageController {

  @Autowired
  private RecommendService recommendService;

  /**
   * <pre>默认进入到首页</pre>
   *
   * @param modelMap
   * @return
   */
  @GetMapping("/")
  public String index(ModelMap modelMap) {
    return "redirect:/index";
  }

  /**
   * <pre>跳转到home页面</pre>
   *
   * @param modelMap
   * @return
   */
  @GetMapping("/index")
  public String homePage(ModelMap modelMap) {
    List<HouseDO> houseList = recommendService.getLastest(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses",houseList);
    return "homepage/index";
  }
}
