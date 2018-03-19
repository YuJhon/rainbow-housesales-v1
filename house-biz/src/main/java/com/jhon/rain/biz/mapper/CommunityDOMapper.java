package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.CommunityDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityDOMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(CommunityDO record);

  int insertSelective(CommunityDO record);

  CommunityDO selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(CommunityDO record);

  int updateByPrimaryKey(CommunityDO record);

  List<CommunityDO> selectCommunity(CommunityDO community);
  
  List<CommunityDO> findAll();
}