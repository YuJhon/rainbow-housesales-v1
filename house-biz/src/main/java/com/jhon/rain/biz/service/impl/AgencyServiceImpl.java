package com.jhon.rain.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jhon.rain.biz.mapper.AgencyDOMapper;
import com.jhon.rain.biz.mapper.UserDOMapper;
import com.jhon.rain.biz.service.AgencyService;
import com.jhon.rain.common.model.AgencyDO;
import com.jhon.rain.common.model.UserDO;
import com.jhon.rain.common.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 23:45
 */
@Service
@Transactional
public class AgencyServiceImpl implements AgencyService {

  @Autowired
  private AgencyDOMapper agencyDOMapper;

  @Autowired
  private UserDOMapper userDOMapper;

  @Value("${file.prefix}")
  private String imgPrefix;

  @Override
  public List<AgencyDO> getAllAgency() {
    return agencyDOMapper.findAll();
  }

  @Override
  public PageData<UserDO> getPageAgency(Integer pageNum, Integer pageSize) {
    Page<UserDO> pageInfo = PageHelper.startPage(pageNum, pageSize);
    List<UserDO> agencies = userDOMapper.selectAgent(new UserDO());
    setImg(agencies);
    long count = pageInfo.getTotal();
    return PageData.buildPage(agencies, count, pageSize, pageNum);
  }

  /**
   * <pre>设置图片的</pre>
   *
   * @param list
   */
  private void setImg(List<UserDO> list) {
    list.forEach(i -> {
      i.setAvatar(imgPrefix + i.getAvatar());
    });
  }


  @Override
  public UserDO getAgentDetail(Long userId) {
    Integer userType = 2;
    List<UserDO> users = userDOMapper.getUserInfo(userId, userType);
    if (users != null && !users.isEmpty()) {
      UserDO agent = users.get(0);
      /** 查询经纪人关联的经纪结构查询出来 **/
      AgencyDO agency = agencyDOMapper.selectByPrimaryKey(agent.getAgencyId());
      if (agency != null) {
        agent.setAgencyName(agency.getName());
      }
      return agent;
    }
    return null;
  }
}
