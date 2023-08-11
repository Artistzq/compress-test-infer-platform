package com.kerbalogy.ctip.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerbalogy.ctip.auth.entity.UserRole;
import com.kerbalogy.ctip.auth.mapper.UserRoleMapper;
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
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    public List<UserRole> getByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return super.list(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId,Long[] roleIds) {
        List<UserRole> userRoles = new ArrayList<UserRole>();
        Arrays.stream(roleIds).forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoles.add(userRole);
        });

        super.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>();
        queryWrapper.eq(UserRole::getUserId, userId);
        super.remove(queryWrapper);
    }
}
