package com.zwj.flower.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.flower.mapper.FlowerMapper;
import com.zwj.flower.pojo.Flower;
import com.zwj.flower.pojo.Page;
import com.zwj.flower.pojo.User;
import com.zwj.flower.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:59
 * @Description:
 */
@Service
public class FlowerServiceImpl implements FlowerService {
    @Autowired
    private FlowerMapper flowerMapper;
    @Override
    public List<Flower> getAllFlowers(Integer userId) {
        return flowerMapper.getAllFlowers(userId);
    }

    @Override
    public Flower getFlowerById(Integer id) {
        return flowerMapper.getFlowerById(id);
    }

    @Override
    public List<Flower> getFlowersByTypeId(Integer typeId, Integer userId) {
        return flowerMapper.getFlowersByTypeId(typeId,userId);
    }

    @Override
    public Page<Flower> getFlowerPage(Integer pageNo, Integer rows,Integer userId) {
        PageHelper.startPage(pageNo,rows);//设置起始页
        PageInfo<Flower> pageInfo = new PageInfo<>(this.getAllFlowers(userId));//直接使用PageInfo取用
        Page<Flower> page = new Page<>();
        page.setStart(1);//设置起始页
        page.setEnd(pageInfo.getPages());//设置尾页
        page.setCurrentPage(pageInfo.getPageNum());//设置当前页
        page.setTotal(pageInfo.getTotal());//设置总条目数
        page.setList(pageInfo.getList());//设置所获取的集合
        return page;
    }

    @Override
    public int addFlower(Flower flower) {
        return flowerMapper.addFlower(flower);
    }

    @Override
    public int updateFlower(Flower flower) {
        return flowerMapper.updateFlower(flower);
    }

    @Override
    public int deleteFlower(Integer id) {
        return flowerMapper.deleteFlower(id);
    }
}
