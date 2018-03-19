package com.jhon.rain.biz.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>功能描述</br>文件上传的逻辑接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 15:35
 */
public interface FileService {
  /**
   * <pre>上传图片</pre>
   *
   * @param avatarFiles
   * @return
   */
  List<String> getImgPaths(List<MultipartFile> avatarFiles);
}
