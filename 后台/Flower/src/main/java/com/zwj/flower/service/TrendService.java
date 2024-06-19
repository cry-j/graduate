package com.zwj.flower.service;

import com.zwj.flower.pojo.Trend;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/22 19:35
 * @Description: trend的service
 */
public interface TrendService {
    List<Trend> getTrendByFlowerId(Integer typeId);//根据花卉id获取趋势
}
