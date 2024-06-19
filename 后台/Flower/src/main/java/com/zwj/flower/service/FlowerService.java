package com.zwj.flower.service;

import com.zwj.flower.pojo.Flower;
import com.zwj.flower.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:58
 * @Description: flower的service
 */
public interface FlowerService {
    List<Flower> getAllFlowers(Integer userId);//根据用户获取所有花卉
    Flower getFlowerById(Integer id);//根据花卉id获取花卉
    List<Flower> getFlowersByTypeId(@Param("typeId") Integer typeId, @Param("userId") Integer userId);//根据typeId获取花卉
    Page<Flower> getFlowerPage(Integer pageNo, Integer rows,Integer userId);//分页
    int addFlower(Flower flower);//增加
    int updateFlower(Flower flower);//修改
    int deleteFlower(Integer id);//删除
}
