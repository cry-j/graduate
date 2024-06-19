package com.zwj.flower.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： Jing
 * @Date: 2024/4/10 18:16
 * @Description: admin的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Integer id;//adminId
    private String userName;//adminName
    private String userPwd;//adminPassword
}
