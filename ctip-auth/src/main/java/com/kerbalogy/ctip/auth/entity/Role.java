package com.kerbalogy.ctip.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description 角色表
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "tb_role")
public class Role extends BaseEntity {

    /**
     * 角色类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

}
