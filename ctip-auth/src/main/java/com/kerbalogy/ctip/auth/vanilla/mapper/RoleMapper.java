package com.kerbalogy.ctip.auth.vanilla.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerbalogy.ctip.auth.vanilla.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-11
 * @description
 **/
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);

}
