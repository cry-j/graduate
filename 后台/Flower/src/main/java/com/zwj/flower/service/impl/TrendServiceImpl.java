package com.zwj.flower.service.impl;

import com.zwj.flower.mapper.TrendMapper;
import com.zwj.flower.pojo.Trend;
import com.zwj.flower.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/22 19:35
 * @Description:
 */
@Service
public class TrendServiceImpl implements TrendService {
    @Autowired
    private TrendMapper trendMapper;
    @Override
    public List<Trend> getTrendByFlowerId(Integer typeId) {
        return trendMapper.getTrendByFlowerId(typeId);
    }
}
