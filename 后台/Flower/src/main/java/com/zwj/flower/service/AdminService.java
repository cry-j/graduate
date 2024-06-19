package com.zwj.flower.service;

import com.zwj.flower.pojo.Admin;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/10 18:25
 * @Description: admin的service
 */
public interface AdminService {
    List<Admin> getAllAdmin();//获取所有admin
    Admin getAdminById(Integer id);//根据id获取admin
    Admin getAdminByName(String userName);//根据名称获取admin
    int addAdmin(Admin admin);//添加
    int updateAdmin(Admin admin);//修改
    int deleteAdmin(Integer id);//删除
}
