package com.jhon.rain.biz.service;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/13 11:41
 */
public interface MailService {
  /**
   * <pre>注册通知</pre>
   *
   * @param email 邮件
   */
  void registerNotify(String email);

  /**
   * <pre>激活用户</pre>
   *
   * @param key
   * @return
   */
  boolean enableUser(String key);

  /**
   * <pre>从缓存中获取邮箱地址</pre>
   *
   * @param key 缓存唯一标识
   * @return key对应的value:email
   */
  String getResetEmail(String key);

  /**
   * <pre>清除缓存</pre>
   *
   * @param key 键
   */
  void invalidateRestKey(String key);

  /**
   * <pre>重置通知邮件</pre>
   *
   * @param email
   */
  void resetNotify(String email);

  /**
   * <pre>发送自定义的邮件</pre>
   *
   * @param subject 主题
   * @param content 内容
   * @param email   邮箱
   */
  void sendCustomEmail(String subject, String content, String email);
}
