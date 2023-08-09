package com.kerbalogy.ctip.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerbalogy.ctip.auth.entity.RolePermission;
import com.kerbalogy.ctip.auth.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Transactional(rollbackFor = Exception.class)
    public void setRolePermission(Long roleId, Long[] permissionIds) {
        deleteByRoleId(roleId);

        if (permissionIds == null) {
            return;
        }

        List<RolePermission> rolePermissions = new ArrayList<>();
        Arrays.stream(permissionIds).forEach(permissionId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        });
        super.saveBatch(rolePermissions);
    }

    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId);
        super.remove(queryWrapper);
    }

}
