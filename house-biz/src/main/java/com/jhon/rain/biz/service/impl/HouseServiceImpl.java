package com.jhon.rain.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.jhon.rain.biz.mapper.CommunityDOMapper;
import com.jhon.rain.biz.mapper.HouseDOMapper;
import com.jhon.rain.biz.mapper.HouseMsgDOMapper;
import com.jhon.rain.biz.mapper.HouseUserDOMapper;
import com.jhon.rain.biz.service.AgencyService;
import com.jhon.rain.biz.service.FileService;
import com.jhon.rain.biz.service.HouseService;
import com.jhon.rain.biz.service.MailService;
import com.jhon.rain.common.enums.HouseUserTypeEnum;
import com.jhon.rain.common.model.*;
import com.jhon.rain.common.page.PageData;
import com.jhon.rain.common.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/6 23:43
 */
@Transactional
@Service
public class HouseServiceImpl implements HouseService {

  @Autowired
  private HouseDOMapper houseDOMapper;

  @Autowired
  private HouseUserDOMapper houseUserDOMapper;

  @Autowired
  private CommunityDOMapper communityDOMapper;

  @Autowired
  private HouseMsgDOMapper houseMsgDOMapper;

  @Autowired
  private AgencyService agencyService;

  @Autowired
  private MailService mailService;

  @Value("${file.prefix}")
  private String imgPrefix;

  @Autowired
  private FileService fileService;

  @Override
  public List<HouseDO> queryAll() {
    return houseDOMapper.findAll();
  }

  @Override
  public List<HouseDO> queryByPage(Integer pageNum, Integer pageSize) {
    Page<HouseDO> pageInfo = PageHelper.startPage(pageNum, pageSize);
    pageInfo.getTotal();
    return houseDOMapper.findAll();
  }

  @Override
  public List<HouseDO> queryAndSetImg(Integer pageNum, Integer pageSize) {
    HouseDO houseQuery = new HouseDO();
    houseQuery.setSort("create_time");
    return this.queryAndSetImg(houseQuery,pageNum,pageSize);
  }

  @Override
  public List<HouseDO> queryAndSetImg(HouseDO queryHouse, Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<HouseDO> houseList = houseDOMapper.recommendHouses(queryHouse);
    houseList.forEach(house -> {
      house.setFirstImg(imgPrefix + house.getFirstImg());
      house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
      house.setFloorPlanList(house.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    });
    return houseList;
  }

  @Override
  public PageData<HouseDO> queryHouse(String name, Integer type, Integer pageNum, Integer pageSize) {
    HouseDO houseQuery = new HouseDO();
    houseQuery.setType(type);
    houseQuery.setName(name);
    return this.constructHouseInfo(houseQuery, pageNum, pageSize);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addHouse(HouseDO house, UserDO user) {
    /** 1.添加房屋图片 **/
    if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
      String images = Joiner.on(",").join(fileService.getImgPaths(house.getHouseFiles()));
      house.setImages(images);
    }
    /** 2.添加户型图片 **/
    if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
      String imgs = Joiner.on(",").join(fileService.getImgPaths(house.getFloorPlanFiles()));
      house.setFloorPlan(imgs);
    }
    /** 3.插入房产信息 **/
    BeanHelper.onInsert(house);
    int record = houseDOMapper.insert(house);
    /** 4.绑定用户到房产的关系 **/
    bindUser2House(house.getId(), user.getId(), false);
    return record;
  }

  @Override
  public HouseDO queryByPrimaryKey(Long houseId) {
    HouseDO houseDO =  houseDOMapper.selectByPrimaryKey(houseId);
    houseDO.setFirstImg(imgPrefix + houseDO.getFirstImg());
    houseDO.setImageList(houseDO.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    houseDO.setFloorPlanList(houseDO.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    return houseDO;
  }

  @Override
  public HouseUserDO getHouseUserByHouserId(Long houseId) {
    return houseUserDOMapper.selectSaleHouseUser(houseId);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addHouseMsg(HouseMsgDO houseMsg) {
    BeanHelper.onInsert(houseMsg);
    int record = houseMsgDOMapper.insert(houseMsg);
    UserDO agent = agencyService.getAgentDetail(houseMsg.getUserId());
    mailService.sendCustomEmail("来自用户" + houseMsg.getEmail() + "的留言", houseMsg.getMsg(), agent.getEmail());
    return record;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateRating(Long id, Double rating) {
    HouseDO house = houseDOMapper.selectByPrimaryKey(id);
    Double oldRating = house.getRating();
    Double newRating = oldRating.equals(0D) ? rating : Math.min((oldRating + rating) / 2, 5);
    HouseDO updateHouse = new HouseDO();
    updateHouse.setId(id);
    updateHouse.setRating(newRating);
    BeanHelper.onUpdate(newRating);
    return houseDOMapper.updateHouse(updateHouse);
  }

  /**
   * <pre>绑定用户到房产的关系</pre>
   *
   * @param houseId 房产Id
   * @param userId  用户Id
   * @param collect 是否收藏
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void bindUser2House(Long houseId, Long userId, boolean collect) {
    Integer type = collect ? HouseUserTypeEnum.BOOKMARK.value : HouseUserTypeEnum.SALE.value;
    HouseUserDO existHouseUser = houseUserDOMapper.selectHouseUser(userId, houseId, type);
    if (existHouseUser != null) {
      return;
    }
    HouseUserDO houseUser = new HouseUserDO();
    houseUser.setHouseId(houseId);
    houseUser.setUserId(userId);
    houseUser.setType(type);
    BeanHelper.setDefaultProp(houseUser, HouseUserDO.class);
    BeanHelper.onInsert(houseUser);
    /** 插入记录 **/
    houseUserDOMapper.insert(houseUser);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void unbindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark) {
    /** 取消收藏  **/
    if (HouseUserTypeEnum.SALE.equals(bookmark)) {
      houseDOMapper.downHouse(houseId);
    } else {
      houseUserDOMapper.deleteHouseUser(houseId, userId, bookmark.value);
    }
  }

  @Override
  public PageData<HouseDO> queryHouse(HouseDO houseInfo, Integer pageNum, Integer pageSize) {
    HouseDO houseQuery = new HouseDO();
    houseQuery.setType(houseInfo.getType());
    houseQuery.setName(houseInfo.getName());
    houseQuery.setBookmarked(houseInfo.isBookmarked());
    return this.constructHouseInfo(houseQuery, pageNum, pageSize);
  }

  /**
   * <pre>查询房产相关信息</pre>
   *
   * @param houseQuery 房产查询的相关信息的封装
   * @param pageNum    当前页
   * @param pageSize   每页的大小
   * @return 封装好的分页房产信息
   */
  private PageData<HouseDO> constructHouseInfo(HouseDO houseQuery, Integer pageNum, Integer pageSize) {
    if (!Strings.isNullOrEmpty(houseQuery.getName())) {
      CommunityDO community = new CommunityDO();
      community.setName(houseQuery.getName());
      List<CommunityDO> communities = communityDOMapper.selectCommunity(community);
      if (!communities.isEmpty()) {
        houseQuery.setCommunityId(communities.get(0).getId());
      }
    }
    /** 查询房产 **/
    Page<HouseDO> pageInfo = PageHelper.startPage(pageNum, pageSize);
    List<HouseDO> houses = houseDOMapper.selectQueryHouse(houseQuery);
    houses.forEach(house -> {
      house.setFirstImg(imgPrefix + house.getFirstImg());
      house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
      house.setFloorPlanList(house.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    });

    long count = pageInfo.getTotal();
    return PageData.buildPage(houses, count, pageSize, pageNum);
  }
}
