package com.kerbalogy.ctip.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kerbalogy.ctip.auth.entity.LoginUser;
import com.kerbalogy.ctip.auth.entity.Role;
import com.kerbalogy.ctip.auth.entity.SecurityUser;
import com.kerbalogy.ctip.auth.mapper.RoleMapper;
import com.kerbalogy.ctip.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 只负责获取，不负责验证
        LambdaQueryWrapper<LoginUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoginUser::getUsername, username);
        LoginUser user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        SecurityUser securityUser = new SecurityUser(user);
        Role role = roleMapper.selectById(securityUser.getRoleId());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        securityUser.setRoles(roles);

        return securityUser;
    }
}
