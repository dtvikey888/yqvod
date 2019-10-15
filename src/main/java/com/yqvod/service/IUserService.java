package com.yqvod.service;

import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.User;

/**
 * 项目名称：yqvod
 * 类 名 称：IUserService
 * 类 描 述：TODO
 * 创建时间：2019/10/15 3:39 PM
 * 创 建 人：fjw
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
}
