package com.zwj.flower.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.flower.mapper.TypeMapper;
import com.zwj.flower.pojo.Page;
import com.zwj.flower.pojo.Type;
import com.zwj.flower.pojo.User;
import com.zwj.flower.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:30
 * @Description:
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Override
    public List<Type> getAllFlowersType() {
        return typeMapper.getAllFlowersType();
    }

    @Override
    public Type getTypeById(Integer typeId) {
        return typeMapper.getTypeById(typeId);
    }

    @Override
    public List<Type> getTypeByName(String typeName) {
        return typeMapper.getTypeByName(typeName);
    }

    @Override
    public Page<Type> getTypePage(Integer pageNo, Integer rows) {
        PageHelper.startPage(pageNo,rows);//设置起始页
        PageInfo<Type> pageInfo = new PageInfo<>(this.getAllFlowersType());//直接使用PageInfo取用
        Page<Type> page = new Page<>();
        page.setStart(1);//设置起始页
        page.setEnd(pageInfo.getPages());//设置尾页
        page.setCurrentPage(pageInfo.getPageNum());//设置当前页
        page.setTotal(pageInfo.getTotal());//设置总条目数
        page.setList(pageInfo.getList());//设置所获取的集合
        return page;
    }

    @Override
    public int addType(Type type) {
        return typeMapper.addType(type);
    }

    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public int deleteType(Integer typeId) {
        return typeMapper.deleteType(typeId);
    }
}
