package com.kerbalogy.ctip.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerbalogy.ctip.auth.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findByRoleIds(@Param("roleIds") List<Long> roleIds);
}
