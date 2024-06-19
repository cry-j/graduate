package com.zwj.flower.controller;

import com.zwj.flower.pojo.Admin;
import com.zwj.flower.pojo.User;
import com.zwj.flower.service.AdminService;
import com.zwj.flower.service.UserService;
import com.zwj.flower.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Author： Jing
 * @Date: 2024/4/10 18:26
 * @Description: 登录界面控制器
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    /**
     * @date: 2024/5/27 10:03
     * @description:  管理员登录校验功能，实现判断管理员是否存在与密码是否正确
     * @param: [java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest]
     * @return: java.lang.String
     */
    @PostMapping("/adminLogin.do")
    public String login(String uname, String uPwd, HttpServletRequest request) throws IOException {
        Admin admin = adminService.getAdminByName(uname);
        if (admin!=null){
            if (admin.getUserPwd().equals(uPwd)){
                HttpSession session = request.getSession();
                session.setAttribute("admin",admin);
                return "登录成功";
            }
            else {
                return "密码错误";
            }
        }else {
           return "用户不存在";
        }
    }
    /**
     * @date: 2024/5/27 10:05
     * @description:  测试用
     * @param: []
     * @return: java.lang.String
     */
    @RequestMapping("/test.do")
    public String test(){
        return "success!";
    }
    /**
     * @date: 2024/5/27 10:07
     * @description:  用户登录功能，实现了用户信息校验功能（含当前用户状态）
     * @param: [java.lang.String, java.lang.String]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.User>
     */
    @PostMapping("/userLogin.do")
    public Result<User> userLogin(String uname, String uPwd){
        User user = userService.getUserByUserName(uname);
        if (user!=null){
            if (user.getState()==0){
                return Result.fail(999,"您的用户已被封禁，请联系管理员解封");
            }else {
                if (user.getUserPwd().equals(uPwd)){
                    user.setLoginDate(new Date());
                    //更新登录时间
                    userService.updateUser(user);
                    return Result.success(user);
                }else {
                    return Result.fail(999,"密码错误");
                }
            }

        }else {
            return Result.fail(999,"用户不存在");
        }
    }
    /**
     * @date: 2024/5/27 10:09
     * @description:  实现用户注册功能
     * @param: [java.lang.String, java.lang.String]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.User>
     */
    @PostMapping("/register.do")
    public Result<User> register(String uname, String uPwd){
        User user = new User();
            user.setUserName(uname);
            user.setUserPwd(uPwd);
            user.setName("初始名称");
            user.setImgPic("default.png");
            userService.addUser(user);
            return Result.success(userService.getUserByUserName(uname));
    }
    /**
     * @date: 2024/5/27 10:09
     * @description:  实现注册时用户名是否重复
     * @param: [java.lang.String]
     * @return: boolean
     */
    @RequestMapping("/checkName.do")
    public boolean checkName(String uname){
        return userService.getUserByUserName(uname) == null;
    }
}
