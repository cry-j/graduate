package com.zwj.flower.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： Jing
 * @Date: 2024/4/22 19:30
 * @Description: 用于返回趋势数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trend {
    private Integer id;//id
    private Integer typeId;//花卉id
    private Integer month;//月份
    private Float min;//最低温度
    private Float max;//最高温度
}
