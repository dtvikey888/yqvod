package com.yqvod.controller.backend;

import com.yqvod.common.Const;
import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.User;
import com.yqvod.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: dtvikey
 * @Date: 12/06/19 下午 02:25
 * @Version 1.0
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if (response.isSuccess()){
            User user = response.getData();
            if (user.getRole()==Const.Role.ROLE_ADMIN){
                //管理员登录
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员无法登录");
            }
        }
        return response;
    }

    //用户列表 /manage/user/list.do
    @RequestMapping(value = "/list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,@RequestParam(value = "pageSize",defaultValue = "5")int pageSize,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null) {
            return ServerResponse.createByErrorMessage("请登录后再试");
        }
        if(Const.Role.ROLE_ADMIN != user.getRole()) {
            return ServerResponse.createByErrorMessage("非管理员访问");
        }
        return iUserService.list(pageNum, pageSize);
    }


}