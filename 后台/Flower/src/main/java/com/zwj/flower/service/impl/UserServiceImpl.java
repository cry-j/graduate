package com.zwj.flower.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.flower.mapper.UserMapper;
import com.zwj.flower.pojo.Page;
import com.zwj.flower.pojo.User;
import com.zwj.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/14 17:31
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public List<User> multiCriteriaQuery(String name, Integer age, String sex, String address, Integer state) {
        return userMapper.multiCriteriaQuery(name,age,sex,address,state);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public Page<User> getUserPage(Integer pageNo, Integer rows) {
        PageHelper.startPage(pageNo,rows);//设置起始页
        PageInfo<User> pageInfo = new PageInfo<>(this.getAllUser());//直接使用PageInfo取用
        Page<User> page = new Page<>();
        page.setStart(1);//设置起始页
        page.setEnd(pageInfo.getPages());//设置尾页
        page.setCurrentPage(pageInfo.getPageNum());//设置当前页
        page.setTotal(pageInfo.getTotal());//设置总条目数
        page.setList(pageInfo.getList());//设置所获取的集合
        return page;
    }
}
