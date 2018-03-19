package com.jhon.rain.biz.service;

import com.jhon.rain.common.enums.HouseUserTypeEnum;
import com.jhon.rain.common.model.HouseDO;
import com.jhon.rain.common.model.HouseMsgDO;
import com.jhon.rain.common.model.HouseUserDO;
import com.jhon.rain.common.model.UserDO;
import com.jhon.rain.common.page.PageData;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/6 23:42
 */
public interface HouseService {
  /**
   * <pre>查询所有记录</pre>
   *
   * @return
   */
  List<HouseDO> queryAll();

  /**
   * <pre>分页查询数据</pre>
   *
   * @param pageNum  当前页
   * @param pageSize 每页显示条数
   * @return 列表
   */
  List<HouseDO> queryByPage(Integer pageNum, Integer pageSize);

  /**
   * <pre>查询最新列表</pre>
   *
   * @param pageNum
   * @param pageSize
   * @return
   */
  List<HouseDO> queryAndSetImg(Integer pageNum, Integer pageSize);

  /**
   * <pre>查询最细列表</pre>
   *
   * @param queryHouse 房产的信息载体
   * @param pageNum    当前要查询的页数
   * @param pageSize   每页查询的条数
   * @return 房产列表
   */
  List<HouseDO> queryAndSetImg(HouseDO queryHouse, Integer pageNum, Integer pageSize);

  /**
   * <pre>分页查询房产列表</pre>
   *
   * @param name     小区名称
   * @param type     房屋类型
   * @param pageNum  当前页码
   * @param pageSize 每页大小
   * @return 分页查询房产列表
   */
  PageData<HouseDO> queryHouse(String name, Integer type, Integer pageNum, Integer pageSize);

  /**
   * <pre>添加房产信息</pre>
   *
   * @param house 房产信息
   * @param user  当前操作的用户
   * @return 返回影响的记录行数
   */
  int addHouse(HouseDO house, UserDO user);

  /**
   * <pre>通过主键查询房产信息</pre>
   *
   * @param houseId 房产Id
   * @return
   */
  HouseDO queryByPrimaryKey(Long houseId);

  /**
   * <pre>通过房产id查询房产用户关系</pre>
   *
   * @param houseId 房产Id
   * @return
   */
  HouseUserDO getHouseUserByHouserId(Long houseId);

  /**
   * <pre>给房产添加留言</pre>
   *
   * @param houseMsg
   * @return
   */
  int addHouseMsg(HouseMsgDO houseMsg);

  /**
   * <pre>更新评分</pre>
   *
   * @param id
   * @param rating
   * @return
   */
  int updateRating(Long id, Double rating);

  /**
   * <pre>收藏房产</pre>
   *
   * @param id     房产Id
   * @param userId 用户Id
   * @param b
   * @return
   */
  void bindUser2House(Long id, Long userId, boolean b);

  /**
   * <pre>取消收藏房产</pre>
   *
   * @param houseId  房产Id
   * @param userId   用户Id
   * @param bookmark 收藏房屋用户类型
   * @return
   */
  void unbindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark);

  /**
   * <pre>查询房产信息</pre>
   *
   * @param house    房间信息
   * @param pageNum  当前页
   * @param pageSize 每页大小
   * @return
   */
  PageData<HouseDO> queryHouse(HouseDO house, Integer pageNum, Integer pageSize);
}
