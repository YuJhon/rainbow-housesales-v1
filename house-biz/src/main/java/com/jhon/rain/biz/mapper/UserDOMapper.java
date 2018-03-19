package com.jhon.rain.biz.mapper;

import com.jhon.rain.common.model.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>用户接口实现</pre>
 */
@Mapper
public interface UserDOMapper {

  int deleteByPrimaryKey(Long id);

  int insert(UserDO record);

  List<UserDO> selectUserByQuery(UserDO userDO);

  int deleteByEmail(String email);

  int update(UserDO updateUser);

  int addAccount(UserDO account);

  /**
   * <pre>查询用户信息</pre>
   *
   * @param userId   用户id
   * @param userType 用户类型
   * @return
   */
  List<UserDO> getUserInfo(@Param(value = "userId") Long userId, @Param(value = "type") Integer userType);

  /**
   * <pre>查询所有</pre>
   *
   * @return
   */
  List<UserDO> findAll();

  /**
   * <pre>查询经纪人</pre>
   *
   * @return
   */
  List<UserDO> selectAgent(@Param(value = "user") UserDO user);
}