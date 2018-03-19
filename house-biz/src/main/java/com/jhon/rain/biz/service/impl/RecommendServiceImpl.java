package com.jhon.rain.biz.service.impl;

import com.jhon.rain.biz.service.HouseService;
import com.jhon.rain.biz.service.RecommendService;
import com.jhon.rain.common.model.HouseDO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>功能描述</br>推荐业务逻辑接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 10:02
 */
@Service
@Slf4j
public class RecommendServiceImpl implements RecommendService {

  private static final String HOT_HOUSE_KEY = "hot_house";

  private static final String REDIS_SERVER_HOST = "127.0.0.1";

  @Autowired
  private HouseService houseService;

  @Override
  public List<HouseDO> getLastest(Integer recommendSize) {
    return houseService.queryAndSetImg(1, recommendSize);
  }

  @Override
  public void increase(Long houseId) {
    try {
      Jedis jedis = new Jedis(REDIS_SERVER_HOST);
      /** 如果存在，权重就自增1，否则就设置其权重为1 **/
      /** Redis-Cli 命令如下：>>ZINCRBY hot_house 1 houseId **/
      jedis.zincrby(HOT_HOUSE_KEY, 1.0D, houseId + "");
      /** 0代表第一个元素,-1代表最后一个元素，保留热度由低到高末尾10个房产  **/
      jedis.zremrangeByRank(HOT_HOUSE_KEY, 0, -11);
      jedis.close();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public List<Long> getHot() {
    try {
      Jedis jedis = new Jedis(REDIS_SERVER_HOST);
      /** 获取redis中保存的所有最热的房产Id集合（逆序：由高到低） (注意：end:-1 -->代表最后一个元素) **/
      Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
      jedis.close();
      List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
      return ids;
    } catch (Exception e) {
      log.error(e.getMessage());
      return Lists.newArrayList();
    }
  }

  @Override
  public List<HouseDO> getHotHouses(Integer recomSize) {
    HouseDO query = new HouseDO();
    List<Long> ids = getHot();
    ids = ids.subList(0, Math.min(ids.size(), recomSize));
    if (ids.isEmpty()) {
      return Lists.newArrayList();
    }
    query.setIds(ids);
    final List<Long> order = ids;
    Integer pageNum = 1;
    List<HouseDO> houses = houseService.queryAndSetImg(query, pageNum, recomSize);
    if (houses == null) {
      houses = Lists.newArrayList();
    }
    return houses;
  }

}
