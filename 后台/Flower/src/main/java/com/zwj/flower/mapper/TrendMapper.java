package com.zwj.flower.mapper;

import com.zwj.flower.pojo.Trend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/22 19:32
 * @Description: trend的mapper
 */
@Mapper
public interface TrendMapper {
    List<Trend> getTrendByFlowerId(Integer typeId);//根据花卉id获取趋势

}
