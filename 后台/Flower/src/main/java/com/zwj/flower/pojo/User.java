package com.zwj.flower.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author： Jing
 * @Date: 2024/4/14 15:50
 * @Description: user的基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;//userId
    private String userName;//用户名
    private String userPwd;//密码
    private String name;//昵称
    private String sex;//性别
    private Integer age;//年龄
    private String address;//地址
    private String tel;//电话
    private String signature;//个性签名
    private String imgPic;//头像
    @JsonFormat(pattern = "yyyy-MM-dd")//设置返回前台的数据格式
    private Date loginDate;//登录日期
    private Integer state;//状态，1表示已注册，0表示被封禁，2表示已删除
}
