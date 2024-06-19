package com.zwj.flower.mapper;

import com.zwj.flower.pojo.Flower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:37
 * @Description: flower的mapper
 */
@Mapper
public interface FlowerMapper {
    List<Flower> getAllFlowers(Integer userId);//根据用户获取所有花卉
    Flower getFlowerById(Integer id);//根据花卉id获取花卉
    List<Flower> getFlowersByTypeId(@Param("typeId") Integer typeId, @Param("userId") Integer userId);//根据typeId获取花卉
    int addFlower(Flower flower);//增加
    int updateFlower(Flower flower);//修改
    int deleteFlower(Integer id);//删除
}
