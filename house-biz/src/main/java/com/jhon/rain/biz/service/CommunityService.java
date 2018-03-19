package com.jhon.rain.biz.service;

import com.jhon.rain.common.model.CommunityDO;

import java.util.List;

/**
 * <p>功能描述</br>社区的业务逻辑接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 19:31
 */
public interface CommunityService {
  /**
   * 获取所有的社区
   *
   * @return
   */
  List<CommunityDO> getAllCommunities();
}
