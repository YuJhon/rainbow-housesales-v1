package com.jhon.rain.web.controller;

import com.jhon.rain.biz.service.AgencyService;
import com.jhon.rain.biz.service.UserService;
import com.jhon.rain.common.constants.CommonConstants;
import com.jhon.rain.common.model.UserDO;
import com.jhon.rain.common.result.ResultMsg;
import com.jhon.rain.common.utils.HashUtils;
import com.jhon.rain.web.util.UserHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>功能描述</br>用户控制器 </p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 21:47
 */
@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private AgencyService agencyService;

  /**
   * <pre>用户注册</pre>
   *
   * @param account  用户信息
   * @param modelMap 携带参数的对象
   * @return 返回view视图路径
   */
  @RequestMapping("/accounts/register")
  public String register(UserDO account, ModelMap modelMap) {
    if (account == null || account.getName() == null) {
      modelMap.put("agencyList", agencyService.getAllAgency());
      return "user/accounts/register";
    }
    /** 用户验证 **/
    ResultMsg resultMsg = UserHelper.validate(account);
    if (resultMsg.isSuccess()) {
      int record = userService.addAccount(account);
      if (record > 0) {
        modelMap.put("email", account.getEmail());
        return "user/accounts/registerSubmit";
      } else {
        return "redirect:accounts/register?" + resultMsg.asUrlParams();
      }
    } else {
      return "redirect:accounts/register?" + resultMsg.asUrlParams();
    }
  }

  /**
   * <pre>用户激活</pre>
   *
   * @param key 激活用户的key
   * @return
   */
  @GetMapping("/accounts/verify")
  public String activateCodeVerify(@RequestParam(name = "key", required = true) String key) {
    boolean result = userService.enable(key);
    if (result) {
      return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
    } else {
      return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
    }
  }

  /**
   * <pre>查询个人信息</pre>
   *
   * @param request
   * @param user
   * @param modelMap
   * @return
   */
  @RequestMapping("accounts/profile")
  public String profile(HttpServletRequest request, UserDO user, ModelMap modelMap) {
    String email = user.getEmail();
    if (email == null) {
      return "/user/accounts/profile";
    }
    userService.updateUser(user, email);
    UserDO query = new UserDO();
    query.setEmail(email);
    List<UserDO> users = userService.getUserByQuery(query);
    request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
    return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
  }

  /**
   * <pre>修改密码操作</pre>
   *
   * @param email           邮箱
   * @param password        密码
   * @param newPassword     新密码
   * @param confirmPassword 确认密码
   * @return
   */
  @PostMapping("accounts/changePassword")
  public String changePassword(@RequestParam(name = "email", required = true) String email,
                               @RequestParam(name = "password", required = true) String password,
                               @RequestParam(name = "newPassword", required = true) String newPassword,
                               @RequestParam(name = "confirmPassword", required = true) String confirmPassword) {
    UserDO user = userService.auth(email, password);
    if (user == null || !confirmPassword.equals(newPassword)) {
      return "redirect:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
    }
    UserDO updateUser = new UserDO();
    updateUser.setPasswd(HashUtils.encryPassword(newPassword));
    userService.updateUser(updateUser, email);
    return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
  }

  /**
   * <pre>用户登录</pre>
   *
   * @param email    邮箱
   * @param password 密码
   * @param target   目标
   * @param request  请求对象
   * @return 视图名称
   */
  @RequestMapping("/accounts/signin")
  public String signIn(@RequestParam(name = "email",required = false) String email,
                       @RequestParam(name = "password",required = false) String password,
                       @RequestParam(name = "target",required = false) String target,
                       HttpServletRequest request) {
    if (email == null || password == null) {
      request.setAttribute("target", target);
      return "/user/accounts/signin";
    }
    /** 用户的认证 **/
    UserDO user = userService.auth(email, password);
    if (user == null) {
      return "redirect:/accounts/signin?target=" + target + "&email=" + email + "&"
              + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
    } else {
      HttpSession session = request.getSession(true);
      session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
      return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
    }
  }

  /**
   * <pre>用户登出操作</pre>
   *
   * @param request
   * @return
   */
  @GetMapping("accounts/logout")
  public String logOut(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    session.invalidate();
    return "redirect:/index";
  }

  /**
   * <pre>重置密码邮件推送通知</pre>
   *
   * @param email    邮箱地址
   * @param modelMap
   * @return
   */
  @GetMapping("accounts/remember")
  public String remember(@RequestParam(name = "email", required = true) String email,
                         ModelMap modelMap) {
    if (StringUtils.isBlank(email)) {
      return "redirect:/accounts/signin?" + ResultMsg.errorMsg("邮箱不能为空").asUrlParams();
    }
    userService.resetNotify(email);
    modelMap.put("email", email);
    return "user/accounts/remember";
  }

  /**
   * <pre>重置</pre>
   *
   * @param key      key获取邮件
   * @param modelMap
   * @return
   */
  @GetMapping("accounts/reset")
  public String reset(@RequestParam(name = "key", required = true) String key,
                      ModelMap modelMap) {
    String email = userService.getResetEmail(key);
    if (StringUtils.isBlank(email)) {
      return "redirect:/accounts/signin?" + ResultMsg.errorMsg("重置链接已经失效").asUrlParams();
    }
    modelMap.put("email", email);
    modelMap.put("success_key", key);
    return "/user/accounts/reset";
  }

  /**
   * <pre>重置密码</pre>
   *
   * @param key           key值
   * @param passwd        密码
   * @param confirmPasswd 确认密码
   * @param request       请求对象
   * @return
   */
  @PostMapping("/accounts/resetSubmit")
  public String resetSubmit(@RequestParam(name = "key", required = true) String key,
                            @RequestParam(name = "passwd", required = true) String passwd,
                            @RequestParam(name = "confirmPasswd", required = true) String confirmPasswd,
                            HttpServletRequest request) {
    /** 密码和重置密码的校验 **/
    ResultMsg resultMsg = UserHelper.validateResetPassword(key, passwd, confirmPasswd);
    if (!resultMsg.isSuccess()) {
      String suffix = "";
      if (StringUtils.isNotBlank(key)) {
        suffix = "email=" + userService.getResetEmail(key) + "&key=" + key + "&";
      }
      return "redirect:/accounts/reset?" + suffix + resultMsg.asUrlParams();
    }
    /** 发送重置邮件 **/
    UserDO updateUser = userService.reset(key, passwd);
    request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, updateUser);
    return "redirect:/index?" + resultMsg.asUrlParams();
  }
}
