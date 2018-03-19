package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.AgencyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyDOMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(AgencyDO record);

  AgencyDO selectByPrimaryKey(Integer id);

  List<AgencyDO> findAll();
}