package com.zwj.flower.mapper;

import com.zwj.flower.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/14 16:05
 * @Description: user的mapper
 */
@Mapper
public interface UserMapper {
    List<User> getAllUser();//获取所有用户信息
    User getUserById(Integer userId);//根据用户id查询用户
    User getUserByUserName(String userName);//根据用户名获取用户信息
    List<User> multiCriteriaQuery(@Param("name") String name,@Param("age") Integer age,@Param("sex") String sex, @Param("address")String address,@Param("state") Integer state);
    //多条件查询
    int addUser(User user);//添加用户
    int updateUser(User user);//修改用户信息
    int deleteUser(Integer id);//删除用户信息
}
