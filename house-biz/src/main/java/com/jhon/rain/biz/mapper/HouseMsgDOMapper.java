package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.HouseMsgDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMsgDOMapper {

  int deleteByPrimaryKey(Long id);

  int insert(HouseMsgDO record);

  int insertSelective(HouseMsgDO record);

  HouseMsgDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(HouseMsgDO record);

  int updateByPrimaryKey(HouseMsgDO record);
}