package com.kerbalogy.ctip.auth.security.service;

import com.kerbalogy.ctip.auth.entity.User;
import com.kerbalogy.ctip.auth.entity.UserRole;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.service.UserRoleService;
import com.kerbalogy.ctip.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userService.getByUsername(username))
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }
    public UserDetails createUserDetails(User user) {
        List<Long> roleIds = userRoleService.getByUserId(user.getId()).stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        return new SecurityUserDetails(user, roleIds);
    }
}
