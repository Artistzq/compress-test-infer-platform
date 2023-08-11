package com.kerbalogy.ctip.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerbalogy.ctip.auth.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
