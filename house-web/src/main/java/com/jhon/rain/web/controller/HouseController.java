package com.jhon.rain.web.controller;

import com.jhon.rain.biz.service.*;
import com.jhon.rain.common.constants.CommonConstants;
import com.jhon.rain.common.enums.HouseUserTypeEnum;
import com.jhon.rain.common.model.*;
import com.jhon.rain.common.page.PageData;
import com.jhon.rain.common.result.ResultMsg;
import com.jhon.rain.web.interceptor.UserContext;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>功能描述</br>房产控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 17:14
 */
@Controller
public class HouseController {

  @Autowired
  private HouseService houseService;

  @Autowired
  private RecommendService recommendService;

  @Autowired
  private CityService cityService;

  @Autowired
  private CommunityService communityService;

  @Autowired
  private CommentService commentService;

  @Autowired
  private AgencyService agencyService;

  /**
   * <pre>房产列表</pre>
   *
   * @param pageNum  当前页
   * @param pageSize 每页大小
   * @param name     小区名称
   * @param type     房屋类型
   * @return 房产列表
   */
  @RequestMapping("/house/list")
  public String queryByPage(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "type", required = false) Integer type,
                            ModelMap modelMap
  ) {
    /** 查询房产列表 **/
    HouseDO query = new HouseDO();
    query.setName(name);
    query.setType(type);
    PageData<HouseDO> ps = houseService.queryHouse(name, type, pageNum, pageSize);
    List<HouseDO> hotHouses = recommendService.getLastest(CommonConstants.RECOM_SIZE);
    modelMap.put("ps", ps);
    modelMap.put("recomHouses", hotHouses);
    modelMap.put("vo", query);
    return "house/listing";
  }

  /**
   * <pre>跳转到添加房产的页面</pre>
   *
   * @param modelMap
   * @return 添加房产的页面
   */
  @GetMapping("/house/toAdd")
  public String toAdd(ModelMap modelMap) {
    modelMap.put("cities", cityService.getAllCities());
    modelMap.put("communitys", communityService.getAllCommunities());
    return "/house/add";
  }

  /**
   * <pre>添加房产的操作</pre>
   *
   * @param house 房产信息
   * @return 当前登录用户的房产信息查询接口
   */
  @RequestMapping("/house/doAdd")
  public String doAdd(HouseDO house) {
    /** 添加房产的业务 **/
    UserDO user = UserContext.getUser();
    house.setState(CommonConstants.HOUSE_STATE_UP);
    houseService.addHouse(house, user);
    return "redirect:/house/ownlist";
  }

  /**
   * <pre>查看当前操作用户的房产信息</pre>
   *
   * @param house    房产
   * @param pageNum  当前页
   * @param pageSize 每页显示大小
   * @param modelMap 携带参数的容器
   * @return
   */
  @RequestMapping("house/ownlist")
  public String ownList(HouseDO house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
    UserDO user = UserContext.getUser();
    house.setUserId(user.getId());
    house.setBookmarked(false);
    PageData<HouseDO> ps = houseService.queryHouse("", 1, pageNum, pageSize);
    modelMap.put("ps", ps);
    modelMap.put("pageType", "own");
    return "/house/ownlist";
  }

  /**
   * <pre>查询房产的详细信息</pre>
   *
   * @param houseId  房产Id
   * @param modelMap 返回页面携带参数的实体
   * @return
   */
  @RequestMapping("/house/detail")
  public String houseDetail(@RequestParam(name = "id") Long houseId, ModelMap modelMap) {
    /** 1.查询房产信息 **/
    HouseDO house = houseService.queryByPrimaryKey(houseId);
    /** 2.查询房产用户信息 **/
    HouseUserDO houseUser = houseService.getHouseUserByHouserId(houseId);
    /** 3.增加推荐的热度 **/
    recommendService.increase(houseId);
    /** 4.获取评论 **/
    int commentSize = 8;
    List<CommentDO> comments = commentService.getHouseComments(houseId, commentSize);
    if (houseUser.getUserId() != null && !Integer.valueOf(0).equals(houseUser.getUserId())) {
      modelMap.put("agent", agencyService.getAgentDetail(houseUser.getUserId()));
    }
    if (comments == null){
      comments = Lists.newArrayList();
    }
    /** 5.获取推荐的房产 **/
    List<HouseDO> rcHouses = recommendService.getLastest(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", rcHouses);
    modelMap.put("house", house);
    modelMap.put("commentList", comments);
    return "/house/detail";
  }

  /**
   * <pre>留言</pre>
   *
   * @param houseMsg 房产留言信息载体
   * @return 房产详情页
   */
  @RequestMapping("/house/leaveMsg")
  public String houseMsg(HouseMsgDO houseMsg) {
    houseService.addHouseMsg(houseMsg);
    return "redirect:/house/detail?id=" + houseMsg.getHouseId() + ResultMsg.successMsg("留言成功！").asUrlParams();
  }


  /**
   * <pre>1.评分</pre>
   *
   * @param rating 评分
   * @param id     主键ID
   * @return
   */
  @ResponseBody
  @RequestMapping("/house/rating")
  public ResultMsg houseRate(Double rating, Long id) {
    UserDO user = UserContext.getUser();
    houseService.updateRating(id, rating);
    return ResultMsg.successMsg("ok");
  }


  /**
   * <pre>2.收藏</pre>
   *
   * @param id 房产Id
   * @return
   */
  @ResponseBody
  @RequestMapping("house/bookmark")
  public ResultMsg bookmark(Long id) {
    UserDO user = UserContext.getUser();
    houseService.bindUser2House(id, user.getId(), true);
    return ResultMsg.successMsg("ok");
  }

  /**
   * <pre>3.取消收藏</pre>
   *
   * @param id 房产Id
   * @return
   */
  @ResponseBody
  @RequestMapping("house/unbookmark")
  public ResultMsg unbookMark(Long id) {
    UserDO user = UserContext.getUser();
    houseService.unbindUser2House(id, user.getId(), HouseUserTypeEnum.BOOKMARK);
    return ResultMsg.successMsg("ok");
  }

  /**
   * <pre>删除房产</pre>
   *
   * @param id       房产Id
   * @param pageType 类型
   * @return
   */
  @RequestMapping(value = "house/del")
  public String delsale(Long id, String pageType) {
    UserDO user = UserContext.getUser();
    houseService.unbindUser2House(id, user.getId(), pageType.equals("owb") ? HouseUserTypeEnum.SALE : HouseUserTypeEnum.BOOKMARK);
    return "redirect/house/ownlist";
  }

  /**
   * <pre>收藏列表</pre>
   *
   * @param house
   * @param pageNum
   * @param pageSize
   * @param modelMap
   * @return
   */
  @RequestMapping("house/bookmarked")
  public String bookmarkedList(HouseDO house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
    UserDO user = UserContext.getUser();
    house.setBookmarked(true);
    house.setUserId(user.getId());
    PageData<HouseDO> ps = houseService.queryHouse(house, pageNum, pageSize);
    modelMap.put("ps", ps);
    modelMap.put("pageType", "book");
    return "/house/ownlist";
  }
}
