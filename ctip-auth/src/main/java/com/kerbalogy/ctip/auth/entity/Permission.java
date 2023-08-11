package com.kerbalogy.ctip.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "tb_permission")
public class Permission extends BaseEntity{

    /**
     * 权限名称
     */
    @TableField(value = "name")
    private String name;

    /**
     *
     * 权限标识
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 请求地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

    /**
     * 状态 1 启用 0 禁用
     */
    @TableField(value = "status")
    private Integer status;

}
