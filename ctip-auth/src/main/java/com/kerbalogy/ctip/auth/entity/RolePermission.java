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
@TableName(value = "tb_role_permission")
public class RolePermission extends BaseEntity{
    /**
     * 角色 ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 权限 ID
     */
    @TableField(value = "permission_id")
    private Long permissionId;
}
