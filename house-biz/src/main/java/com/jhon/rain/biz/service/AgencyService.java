package com.jhon.rain.biz.service;

import com.jhon.rain.common.model.AgencyDO;
import com.jhon.rain.common.model.UserDO;
import com.jhon.rain.common.page.PageData;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 23:45
 */
public interface AgencyService {
  /**
   * <pre>获取所有的经纪机构</pre>
   *
   * @return
   */
  List<AgencyDO> getAllAgency();

  /**
   * <pre>分页查询经纪人信息</pre>
   *
   * @param pageNum  当前查询的页
   * @param pageSize 每页查询的数据大小
   * @return
   */
  PageData<UserDO> getPageAgency(Integer pageNum, Integer pageSize);

  /**
   * <pre>查找用户所属经纪机构的详细信息</pre>
   *
   * @param userId 用户Id
   * @return 经纪结构详细信息
   */
  UserDO getAgentDetail(Long userId);
}
