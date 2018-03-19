package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.BlogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogDOMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(BlogDO record);

  int insertSelective(BlogDO record);

  BlogDO selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(BlogDO record);

  int updateByPrimaryKeyWithBLOBs(BlogDO record);

  int updateByPrimaryKey(BlogDO record);
}