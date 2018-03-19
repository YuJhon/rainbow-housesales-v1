package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.HouseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseDOMapper {

  int deleteByPrimaryKey(Long id);

  int insert(HouseDO record);

  HouseDO selectByPrimaryKey(Long id);

  /**
   * <pre>查询所有房产</pre>
   *
   * @return
   */
  List<HouseDO> findAll();

  /**
   * <pre>推荐的房产</pre>
   *
   * @param house
   * @return
   */
  List<HouseDO> recommendHouses(@Param(value = "house") HouseDO house);

  /**
   * <pre>查询房产</pre>
   *
   * @param houseQuery
   * @return
   */
  List<HouseDO> selectQueryHouse(HouseDO houseQuery);

  /**
   * <pre>更新房产评分信息</pre>
   *
   * @param updateHouse 需要更新的房产
   * @return
   */
  int updateHouse(HouseDO updateHouse);

  /**
   * <pre>取消收藏</pre>
   *
   * @param houseId 房产ID
   */
  void downHouse(Long houseId);

}