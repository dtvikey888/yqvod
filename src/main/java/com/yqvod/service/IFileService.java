package com.yqvod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: dtvikey
 * @Date: 05/07/19 下午 04:52
 * @Version 1.0
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

}