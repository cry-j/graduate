package com.zwj.flower.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author： Jing
 * @Date: 2024/4/18 20:34
 * @Description: flower的pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flower {
    private Integer id;//花id
    private Integer typeId;//花的类别id
    private Integer userId;//用户id
    private Integer number;//花卉数量
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")//设置返回前台的数据格式
    private Date plantDate;//移植日期
    private Double purchase;//购买价
    private Double sale;//预售价
    private Integer del;//删除编号，0删除，1存在
    private Type type;//多表联查的type
    private User user;//user
}
