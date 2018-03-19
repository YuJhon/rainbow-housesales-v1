package com.jhon.rain.biz.service;

import com.jhon.rain.common.model.CommentDO;

import java.util.List;

/**
 * <p>功能描述</br>房屋评论业务接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 10:10
 */
public interface CommentService {
  /**
   * <pre>获取房产的评论</pre>
   *
   * @param houseId     房产id
   * @param commentSize 查询评论的条数
   * @return
   */
  List<CommentDO> getHouseComments(Long houseId, int commentSize);
}
