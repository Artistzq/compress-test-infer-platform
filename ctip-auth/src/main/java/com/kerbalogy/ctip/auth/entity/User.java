package com.kerbalogy.ctip.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description 用户表
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
public class User extends BaseEntity{
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 注册手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 注册邮箱
     */
    @TableField(value = "email")
    private String email;
}
