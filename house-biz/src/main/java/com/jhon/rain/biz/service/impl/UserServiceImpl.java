package com.jhon.rain.biz.service.impl;

import com.google.common.collect.Lists;
import com.jhon.rain.biz.mapper.UserDOMapper;
import com.jhon.rain.biz.service.FileService;
import com.jhon.rain.biz.service.MailService;
import com.jhon.rain.biz.service.UserService;
import com.jhon.rain.common.model.UserDO;
import com.jhon.rain.common.utils.BeanHelper;
import com.jhon.rain.common.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>功能描述</br>用户业务逻辑接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 21:48
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDOMapper userDOMapper;

  @Autowired
  private MailService mailService;

  @Autowired
  private FileService fileService;

  @Value("${file.prefix}")
  private String imgPrefix;


  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addAccount(UserDO account) {
    account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
    List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
    if (!imgList.isEmpty()) {
      account.setAvatar(imgList.get(0));
    }
    /** 设置默认值 **/
    BeanHelper.setDefaultProp(account, UserDO.class);
    BeanHelper.onInsert(account);
    /** 添加用户：实则激活标识为false **/
    account.setEnable(0);
    int record = userDOMapper.addAccount(account);
    /** 发送激活邮件 **/
    mailService.registerNotify(account.getEmail());
    return record;
  }

  @Override
  public boolean enable(String key) {
    return mailService.enableUser(key);
  }

  @Override
  public UserDO auth(String email, String password) {
    UserDO queryUser = new UserDO();
    queryUser.setEmail(email);
    queryUser.setPasswd(HashUtils.encryPassword(password));
    queryUser.setEnable(1);
    List<UserDO> userList = userDOMapper.selectUserByQuery(queryUser);
    if (!userList.isEmpty()) {
      return userList.get(0);
    }
    return null;
  }

  @Override
  public String getResetEmail(String key) {
    String email = "";
    try {
      email = mailService.getResetEmail(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return email;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public UserDO reset(String key, String passwd) {
    String email = getResetEmail(key);
    UserDO updateUser = new UserDO();
    updateUser.setEmail(email);
    updateUser.setPasswd(HashUtils.encryPassword(passwd));
    /** 更新密码 **/
    userDOMapper.update(updateUser);
    /** 清除缓存中的数据 **/
    mailService.invalidateRestKey(key);
    /** 查询用户信息 **/
    return getUserByEmail(email);
  }

  @Override
  public void resetNotify(String email) {
    mailService.resetNotify(email);
  }

  @Override
  public int updateUser(UserDO user, String email) {
    user.setEmail(email);
    BeanHelper.onUpdate(user);
    userDOMapper.update(user);
    return 0;
  }

  /**
   * <pre>通过邮箱查询用户</pre>
   *
   * @param email 邮件
   * @return
   */
  private UserDO getUserByEmail(String email) {
    UserDO queryUser = new UserDO();
    queryUser.setEmail(email);
    List<UserDO> users = getUserByQuery(queryUser);
    if (!users.isEmpty()) {
      return users.get(0);
    }
    return null;
  }

  /**
   * <pre>获取用户信息，完善头像信息</pre>
   *
   * @param user 待查询的用户
   * @return
   */
  public List<UserDO> getUserByQuery(UserDO user) {
    List<UserDO> list = userDOMapper.selectUserByQuery(user);
    list.forEach(u -> {
      u.setAvatar(imgPrefix + u.getAvatar());
    });
    return list;
  }

}
