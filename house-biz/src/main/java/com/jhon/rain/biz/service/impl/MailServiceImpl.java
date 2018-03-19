package com.jhon.rain.biz.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.jhon.rain.biz.mapper.UserDOMapper;
import com.jhon.rain.biz.service.MailService;
import com.jhon.rain.common.model.UserDO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>功能描述</br>邮件逻辑实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/13 11:42
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private UserDOMapper userMapper;

  @Value("${spring.mail.username}")
  private String from;

  @Value("${domain.name}")
  private String domainName;

  /**
   * 注册的缓存
   */
  private final Cache<String, String> registerCache =
          CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
                  .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                      String email = notification.getValue();
                      UserDO userDO = new UserDO();
                      userDO.setEmail(email);
                      List<UserDO> targetUser = userMapper.selectUserByQuery(userDO);
                      if (!targetUser.isEmpty() && Objects.equals(targetUser.get(0).getEnable(), false)) {
                        userMapper.deleteByEmail(email);
                      }
                    }
                  }).build();

  /**
   * 重置的缓存
   **/
  private final Cache<String, String> resetCache = CacheBuilder.newBuilder()
          .maximumSize(100)
          .expireAfterAccess(15, TimeUnit.MINUTES)
          .build();

  @Override
  public void registerNotify(String email) {
    String randomKey = RandomStringUtils.randomAlphabetic(10);
    registerCache.put(randomKey, email);
    String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
    String prefix = "你好，<br>感谢你注册房产网。<br>";
    String subfix = "请点击以下链接(15分钟内有效)激活账号。";
    sendMail("房产平台激活邮件", url, prefix, subfix, email);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean enableUser(String key) {
    String email = registerCache.getIfPresent(key);
    if (StringUtils.isBlank(email)) {
      log.error("Email Activate User Error,Key={0}", key);
      return false;
    }
    UserDO updateUser = new UserDO();
    updateUser.setEmail(email);
    updateUser.setEnable(1);
    int record = userMapper.update(updateUser);
    if (record > 0) {
      registerCache.invalidate(key);
      return true;
    }
    return false;
  }

  @Override
  public String getResetEmail(String key) {
    return resetCache.getIfPresent(key);
  }

  @Override
  public void invalidateRestKey(String key) {
    resetCache.invalidate(key);
  }

  @Override
  public void resetNotify(String email) {
    String randomKey = RandomStringUtils.randomAlphabetic(10);
    registerCache.put(randomKey, email);
    String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
    String prefix = "你好，<br>重置密码确认。<br>";
    String subfix = "请点击以下链接(15分钟内有效)确认修改密码。";
    sendMail("房产平台重置密码确认邮件", url, prefix, subfix, email);
  }

  @Override
  @Async
  public void sendCustomEmail(String subject, String content, String email) {
    /** 发送简单的邮件 **/
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setSubject(subject);
    message.setTo(email);
    message.setText(content);
    mailSender.send(message);
  }


  /**
   * <pre>邮件发送的异步配置</pre>
   *
   * @param title 主题
   * @param url   嵌入的链接
   * @param email 接收邮件的邮箱
   */
  @Async
  public void sendMail(String title, String url, String prefix, String subfix, String email) {
    MimeMessage message = null;
    String text = url;
    try {
      Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
      configuration.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "static/tpl");
      Template emailTemplate = configuration.getTemplate("email.ftl");
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("email", email);
      model.put("linkUrl", url);
      model.put("prefixContent", prefix);
      model.put("subContent", subfix);
      text = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, model);
      /** 邮件发送 **/
      message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setSubject(title);
      helper.setTo(email);
      /** 这里的第二个参数为true ，代表的是以HTML格式来显示邮件 **/
      helper.setText(text, true);
      mailSender.send(message);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
