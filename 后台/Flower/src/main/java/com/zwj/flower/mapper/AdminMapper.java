package com.zwj.flower.mapper;

import com.zwj.flower.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/10 18:22
 * @Description: admin的mapper
 */
@Mapper
public interface AdminMapper {
    List<Admin> getAllAdmin();//获取所有admin
    Admin getAdminById(Integer id);//根据id获取admin
    Admin getAdminByName(String userName);//根据名称获取admin
    int addAdmin(Admin admin);//添加
    int updateAdmin(Admin admin);//修改
    int deleteAdmin(Integer id);//删除
}
