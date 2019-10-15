package com.yqvod.service.impl;
/**
 * 项目名称：yqvod
 * 类 名 称：UserServiceImpl
 * 类 描 述：TODO
 * 创建时间：2019/10/15 3:41 PM
 * 创 建 人：fjw
 */

import com.yqvod.common.ServerResponse;
import com.yqvod.dao.UserMapper;
import com.yqvod.pojo.User;
import com.yqvod.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName $ {NAME}
 * @Description TODO
 * @Author fjw
 * @Date 2019/10/15 3:41 PM
 * @Version 1.0
 **/
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        // TODO: 2019/10/15  密码登录MD5

        User user = userMapper.selectLogin(username,password);
        if (user==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);

    }
}
