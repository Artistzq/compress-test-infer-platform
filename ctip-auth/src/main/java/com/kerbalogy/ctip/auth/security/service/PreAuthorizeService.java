package com.kerbalogy.ctip.auth.security.service;

import com.kerbalogy.ctip.auth.entity.Permission;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.service.PermissionService;
import com.kerbalogy.ctip.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Component
public class PreAuthorizeService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    public boolean hasPermission(String... permissions) {
        // 获取当前登录用户的信息
        SecurityUserDetails currentUser = SecurityUserDetails.getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        // 获取当前登录用户拥有的角色
        List<Long> roleIds = currentUser.getRoleIds();
        // 判断是否是超级管理员
        boolean isSuperAdmin = roleService.hasAnySuperAdmin(roleIds);
        // 如果是超管，则放行
        if (isSuperAdmin) {
            return true;
        }

        // 获取角色拥有的权限
        List<String> userPermissions = permissionService.findByRoleIds(roleIds).stream()
                .map(Permission::getPerms)
                .filter(Objects::nonNull)
                .toList();

        // 判断是否拥有权限
        return Arrays.stream(permissions).anyMatch(userPermissions::contains);
    }

}
