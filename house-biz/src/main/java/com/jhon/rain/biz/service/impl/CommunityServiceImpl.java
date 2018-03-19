package com.jhon.rain.biz.service.impl;

import com.jhon.rain.biz.mapper.CommunityDOMapper;
import com.jhon.rain.biz.service.CommunityService;
import com.jhon.rain.common.model.CommunityDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>社区业务逻辑实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 19:37
 */
@Service
public class CommunityServiceImpl implements CommunityService {

  @Autowired
  private CommunityDOMapper communityDOMapper;

  @Override
  public List<CommunityDO> getAllCommunities() {
    return communityDOMapper.findAll();
  }
}
