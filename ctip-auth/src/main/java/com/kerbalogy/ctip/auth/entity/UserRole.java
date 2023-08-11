package com.kerbalogy.ctip.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description 用户角色表
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "tb_user_role")
public class UserRole extends BaseEntity{
    /**
     * 用户 ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色 ID
     */
    @TableField(value = "role_id")
    private Long roleId;
}
