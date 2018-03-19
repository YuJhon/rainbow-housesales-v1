package com.jhon.rain.web.controller;

import com.jhon.rain.biz.service.AgencyService;
import com.jhon.rain.biz.service.HouseService;
import com.jhon.rain.biz.service.RecommendService;
import com.jhon.rain.common.constants.CommonConstants;
import com.jhon.rain.common.model.AgencyDO;
import com.jhon.rain.common.model.HouseDO;
import com.jhon.rain.common.model.UserDO;
import com.jhon.rain.common.page.PageData;
import com.jhon.rain.common.result.ResultMsg;
import com.jhon.rain.web.interceptor.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

/**
 * <p>功能描述</br>经纪人控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/17 8:32
 */
@Controller
public class AgencyController {

  @Autowired
  private AgencyService agencyService;

  @Autowired
  private RecommendService recommendService;

  @Autowired
  private HouseService houseService;

  /**
   * <pre>添加经纪人</pre>
   *
   * @return
   */
  @GetMapping("agency/create")
  public String createAgency() {
    UserDO user = UserContext.getUser();
    if (user == null || !Objects.equals("jiangy19@126.com", user.getEmail())) {
      return "redirect:/accounts/signin?" + ResultMsg.successMsg("请先登录").asUrlParams();
    }
    return "user/agency/create";
  }

  /**
   * <pre>查询经纪人列表</pre>
   *
   * @param pageNum  分页的当前页
   * @param pageSize 每页的大小
   * @param modelMap 参数信息载体
   * @return 经纪人列表
   */
  @GetMapping("agency/agentList")
  public String agentList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
                          ModelMap modelMap) {
    PageData<UserDO> ps = agencyService.getPageAgency(pageNum, pageSize);
    List<HouseDO> houses = recommendService.getHotHouses(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("ps", ps);
    return "user/agent/agentList";
  }

  /**
   * <pre>获取经纪人详情</pre>
   *
   * @param id       经纪人Id
   * @param modelMap
   * @return
   */
  @RequestMapping("/agency/agentDetail")
  public String agentDetail(Long id, ModelMap modelMap) {
    UserDO user = agencyService.getAgentDetail(id);
    List<HouseDO> houses = recommendService.getHotHouses(CommonConstants.RECOM_SIZE);
    HouseDO query = new HouseDO();
    query.setUserId(id);
    query.setBookmarked(false);
    PageData<HouseDO> bindHouse = houseService.queryHouse(query, 1, 3);
    if (bindHouse != null) {
      modelMap.put("bindHouses", bindHouse.getList());
    }
    modelMap.put("recomHouses", houses);
    modelMap.put("agent", user);
    modelMap.put("agencyName", user.getAgencyName());
    return "/user/agent/agentDetail";
  }

  /**
   * <pre>获取经纪机构列表</pre>
   *
   * @param modelMap 携带信息到页面
   * @return
   */
  @GetMapping("agency/list")
  public String agencyList(ModelMap modelMap) {
    List<AgencyDO> agencies = agencyService.getAllAgency();
    List<HouseDO> houses = recommendService.getHotHouses(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("agencyList", agencies);
    return "/user/agency/agencyList";
  }

}
