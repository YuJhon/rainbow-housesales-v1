package com.jhon.rain.biz.service.impl;

import com.google.common.collect.Lists;
import com.jhon.rain.biz.service.CityService;
import com.jhon.rain.common.model.CityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>查询城市</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 19:15
 */
@Service
public class CityServiceImpl implements CityService {

  @Override
  public List<CityDTO> getAllCities() {
    CityDTO city = new CityDTO();
    city.setCityCode("110000");
    city.setCityName("北京");
    city.setId(1);
    return Lists.newArrayList(city);
  }
}
