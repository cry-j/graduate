package com.zwj.flower.service;

import com.zwj.flower.pojo.Page;
import com.zwj.flower.pojo.Type;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:29
 * @Description: type的service
 */
public interface TypeService {
    List<Type> getAllFlowersType();//获取全部类别
    Type getTypeById(Integer typeId);//根据id获取类别
    List<Type> getTypeByName(String typeName);//根据名称查询
    Page<Type> getTypePage(Integer pageNo,Integer rows);//分页显示
    int addType(Type type);//增加
    int updateType(Type type);//修改
    int deleteType(Integer typeId);//删除
}
