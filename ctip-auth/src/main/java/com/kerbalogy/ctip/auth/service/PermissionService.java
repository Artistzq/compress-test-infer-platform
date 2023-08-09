package com.kerbalogy.ctip.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerbalogy.ctip.auth.entity.Permission;
import com.kerbalogy.ctip.auth.mapper.PermissionMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> findByRoleIds(List<Long> roleIds) {
        return  permissionMapper.findByRoleIds(roleIds);
    }


}
