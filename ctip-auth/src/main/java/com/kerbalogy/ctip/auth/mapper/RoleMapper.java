package com.kerbalogy.ctip.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerbalogy.ctip.auth.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
