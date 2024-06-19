package com.zwj.flower.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： Jing
 * @Date: 2024/4/18 19:59
 * @Description: flowerType的pojo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    private Integer typeId;//类别编号
    private String typeName;//类别名称
    private String family;//属名
    private String origin;//原产地
    private String florescence;//花期
    private String sunshine;//习性
    private String temperature;//适宜温度
    private String soil;//土质
    private String water;//水分
    private String flowerPic;//花卉图片
    private String description;//一些描述
    private Integer state;//删除标识符，0删除，1存在
}
