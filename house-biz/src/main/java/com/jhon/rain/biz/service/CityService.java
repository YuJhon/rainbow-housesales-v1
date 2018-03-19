package com.jhon.rain.biz.service;

import com.jhon.rain.common.model.CityDTO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 19:15
 */
public interface CityService {
  /**
   * 获取所有的城市
   *
   * @return
   */
  List<CityDTO> getAllCities();
}
