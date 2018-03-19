package com.jhon.rain.web.controller;

import com.jhon.rain.biz.service.AgencyService;
import com.jhon.rain.biz.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/11 22:35
 */
@Controller
public class FreemarkerController {

  @Autowired
  private AgencyService agencyService;

  @Autowired
  private MailService mailService;


  @GetMapping("/test")
  public String home(ModelMap modelMap) {
    modelMap.put("agencyList",  agencyService.getAllAgency());
    return "user/accounts/register";
  }

  @GetMapping("/indexTest")
  public String index(ModelMap modelMap) {
    modelMap.put("user", "Jhon Rain");
    return "index";
  }

  @GetMapping("/sndEmail")
  @ResponseBody
  public String index() {
    mailService.registerNotify("jiangy19@126.com");
    return "success";
  }


}
