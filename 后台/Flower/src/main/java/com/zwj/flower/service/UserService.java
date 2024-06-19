package com.zwj.flower.service;

import com.zwj.flower.pojo.Page;
import com.zwj.flower.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/14 17:30
 * @Description: user的service
 */
public interface UserService {
    List<User> getAllUser();//获取所有用户信息
    User getUserById(Integer userId);//根据用户id查询用户
    User getUserByUserName(String userName);//根据用户名获取用户信息
    List<User> multiCriteriaQuery(String name, Integer age, String sex,String address,Integer state);
    //多条件查询
    int addUser(User user);//添加用户
    int updateUser(User user);//修改用户信息
    int deleteUser(Integer id);//删除用户信息
    Page<User> getUserPage(Integer pageNo,Integer rows);//分页显示
}
