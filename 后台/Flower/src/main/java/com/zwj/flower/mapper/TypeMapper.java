package com.zwj.flower.mapper;

import com.zwj.flower.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:04
 * @Description: flowerType的mapper
 */
@Mapper
public interface TypeMapper {
    List<Type> getAllFlowersType();//获取全部类别
    Type getTypeById(Integer typeId);//根据id获取类别
    List<Type> getTypeByName(String typeName);//根据名称查询
    int addType(Type type);//增加
    int updateType(Type type);//修改
    int deleteType(Integer typeId);//删除
}
