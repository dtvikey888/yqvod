package com.yqvod.service;

import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * 项目名称：yqvod
 * 类 名 称：IUserService
 * 类 描 述：TODO
 * 创建时间：2019/10/15 3:39 PM
 * 创 建 人：fjw
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username,String question,String answer);
    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);
    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);
    ServerResponse<User> updateInformation(User user);
    ServerResponse<User> getInformation(Integer userId);
    ServerResponse checkAdminRole(User user);
    ServerResponse<PageInfo> list(int pageNum,int pageSize);
}
