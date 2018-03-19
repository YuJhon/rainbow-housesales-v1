package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.HouseUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseUserDOMapper {

  int deleteByPrimaryKey(Long id);

  int insert(HouseUserDO record);

  int insertSelective(HouseUserDO record);

  HouseUserDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(HouseUserDO record);

  int updateByPrimaryKey(HouseUserDO record);

  /**
   * <pre>查询用户和房产是否存在绑定关系</pre>
   *
   * @param userId  用户Id
   * @param houseId 房产Id
   * @param type    用户类型
   * @return 返回用户和房产之间的关系对象
   */
  HouseUserDO selectHouseUser(@Param("userId") Long userId, @Param("houseId") Long houseId, @Param("type") Integer type);

  /**
   * <pre>查询对应的房产关系</pre>
   *
   * @param houseId
   * @return
   */
  HouseUserDO selectSaleHouseUser(@Param("houseId") Long houseId);

  /**
   * <pre>删除房产和用户关系记录</pre>
   *
   * @param houseId 房产Id
   * @param userId  用户Id
   * @param type    类型
   */
  void deleteHouseUser(@Param("houseId") Long houseId, @Param("userId") Long userId, @Param("type") Integer type);
}