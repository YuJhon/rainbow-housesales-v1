package com.jhon.rain.biz.service.impl;

import com.jhon.rain.biz.mapper.CommentDOMapper;
import com.jhon.rain.biz.service.CommentService;
import com.jhon.rain.common.model.CommentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>房屋评论业务逻辑接口实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 10:10
 */
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentDOMapper commentDOMapper;

  @Override
  public List<CommentDO> getHouseComments(Long houseId, int commentSize) {

    return null;
  }
}
