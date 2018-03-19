package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.CommentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDOMapper {

  int deleteByPrimaryKey(Long id);

  int insert(CommentDO record);

  int insertSelective(CommentDO record);

  CommentDO selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(CommentDO record);

  int updateByPrimaryKey(CommentDO record);
}