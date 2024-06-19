package com.zwj.flower.controller;

import com.zwj.flower.pojo.Flower;
import com.zwj.flower.pojo.Page;
import com.zwj.flower.pojo.Type;
import com.zwj.flower.pojo.User;
import com.zwj.flower.service.TypeService;
import com.zwj.flower.service.UserService;
import com.zwj.flower.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author： Jing
 * @Date: 2024/4/21 22:14
 * @Description: admin的controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    /**
     * @date: 2024/5/27 10:10
     * @description:  获取用户列表，分页显示，每页显示8条
     * @param: [java.lang.Integer]
     * @return: com.zwj.flower.utils.Result<com.zwj.flower.pojo.User>
     */
    @RequestMapping("/getAllUser.do")
    public Result<User> getAllUser(Integer pageNo){
        Page<User> page = userService.getUserPage(pageNo,8);
        if (page!=null){
            return Result.success(page);
        }else {
            return Result.fail(999,"获取失败");
        }
    }
    /**
     * @date: 2024/5/27 10:14
     * @description:  实现管理员对用户封禁功能
     * @param: [java.lang.Integer, java.lang.Integer]
     * @return: java.lang.String
     */
    @PutMapping("/updateState.do")
    public String setState(Integer state,Integer userId){
        User user = userService.getUserById(userId);
        user.setState(1-state);
        if (userService.updateUser(user)>0){
            if (state==0){
                return "已解封";
            }else {
                return "已封禁";
            }
        }else {
            return "error";
        }
    }
    /**
     * @date: 2024/5/27 10:14
     * @description:  删除用户（假删除）
     * @param: [java.lang.Integer]
     * @return: java.lang.String
     */
    @DeleteMapping("/deleteUser.do")
    public String deleteUser(Integer userId){
        if (userService.deleteUser(userId)>0){
                return "删除成功";
            }else {
            return "error";
        }
    }
    /**
     * @date: 2024/5/27 10:16
     * @description:  添加花卉类别
     * @param: [com.zwj.flower.pojo.Type] @RequestBody
     * @return: com.zwj.flower.utils.Result<java.lang.String>
     */
    @PostMapping("/addType.do")
    public Result<String> addFlowerType(@RequestBody Type type){
        if (typeService.addType(type)>0){
            return Result.success("添加成功");
        }else {
            return Result.fail(999,"添加失败");
        }
    }
    /**
     * @date: 2024/5/27 10:15
     * @description:  修改花卉类别信息
     * @param: [com.zwj.flower.pojo.Type] @RequestBody
     * @return: com.zwj.flower.utils.Result<java.lang.String>
     */
    @PutMapping("/updateType.do")
    public Result<String> updateType(@RequestBody Type type){
        if (typeService.updateType(type)>0){
            return Result.success("修改成功");
        }else {
            return Result.fail(999,"添加失败");
        }
    }
}
