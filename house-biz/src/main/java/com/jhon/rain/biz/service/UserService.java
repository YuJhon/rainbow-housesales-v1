package com.jhon.rain.biz.service;

import com.jhon.rain.common.model.UserDO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 21:48
 */
public interface UserService {
  /**
   * <pre>添加用户</pre>
   *
   * @param account 用户
   * @return 返回影响的记录数
   */
  int addAccount(UserDO account);

  /**
   * <pre>激活用户</pre>
   *
   * @param key 激活key
   * @return
   */
  boolean enable(String key);

  /**
   * <pre>用户的认证</pre>
   *
   * @param email    邮箱
   * @param password 密码
   * @return 用户信息
   */
  UserDO auth(String email, String password);

  /**
   * <pre>密码重置</pre>
   *
   * @param key 从缓存中获取key对应的邮箱
   * @return 返回用户邮箱标识
   */
  String getResetEmail(String key);

  /**
   * <pre>重置用户密码</pre>
   *
   * @param key    邮件缓存key
   * @param passwd 密码
   * @return 更新后的用户信息
   */
  UserDO reset(String key, String passwd);

  /**
   * <pre>重置密码通知邮件</pre>
   *
   * @param email
   */
  void resetNotify(String email);

  /**
   * <pre>更新用户信息</pre>
   *
   * @param user
   * @param email
   * @return
   */
  int updateUser(UserDO user, String email);

  /**
   * <pre>查询用户信息</pre>
   *
   * @param user
   * @return
   */
  List<UserDO> getUserByQuery(UserDO user);
}
