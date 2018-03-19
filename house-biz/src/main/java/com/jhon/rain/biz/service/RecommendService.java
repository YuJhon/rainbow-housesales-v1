package com.jhon.rain.biz.service;

import com.jhon.rain.common.model.HouseDO;

import java.util.List;

/**
 * <p>功能描述</br>推荐房产信息</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 10:02
 */
public interface RecommendService {

  /**
   * <pre>获取最新的房产</pre>
   *
   * @param recommendSize 推荐房产的数量
   * @return 返回推荐的房产信息
   */
  List<HouseDO> getLastest(Integer recommendSize);

  /**
   * <pre>产品的热度</pre>
   *
   * @param houseId 房产ID
   */
  void increase(Long houseId);

  /**
   * <pre>获取最热</pre>
   *
   * @return
   */
  List<Long> getHot();

  /**
   * <pre>获取最热的房产</pre>
   *
   * @param recomSize 排名的个数
   * @return
   */
  List<HouseDO> getHotHouses(Integer recomSize);
}
