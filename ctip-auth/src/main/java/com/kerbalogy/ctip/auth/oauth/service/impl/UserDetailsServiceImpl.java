package com.kerbalogy.ctip.auth.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kerbalogy.ctip.auth.vanilla.dto.UserDetailsDTO;
import com.kerbalogy.ctip.auth.vanilla.entity.UserAuth;
import com.kerbalogy.ctip.auth.vanilla.entity.UserInfo;
import com.kerbalogy.ctip.auth.vanilla.mapper.RoleMapper;
import com.kerbalogy.ctip.auth.vanilla.mapper.UserAuthMapper;
import com.kerbalogy.ctip.auth.vanilla.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-11
 * @description
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("用户名不为空");
        }
        UserAuth userAuth = userAuthMapper.selectOne(
                new LambdaQueryWrapper<UserAuth>()
                        .select(UserAuth::getId, UserAuth::getUserInfoId,
                        UserAuth::getUsername, UserAuth::getPassword, UserAuth::getLoginType)
                        .eq(UserAuth::getUsername, username)
                );
        if (Objects.isNull(userAuth)) {
            throw new RuntimeException("用户不存在");
        }
        return convertUserDetail(userAuth, request);
    }

    public UserDetailsDTO convertUserDetail(UserAuth userAuth, HttpServletRequest httpServletRequest) {
        UserInfo userInfo = userInfoMapper.selectById(userAuth.getUserInfoId());
        List<String> roles = roleMapper.listRolesByUserInfoId(userInfo.getId());
        // TODO 获取IP Source和IP Address UserAgent
        return UserDetailsDTO.builder()
                .id(userAuth.getId())
                .loginType(userAuth.getLoginType())
                .userInfoId(userInfo.getId())
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .email(userInfo.getEmail())
                .roles(roles)
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .website(userInfo.getWebsite())
                .isSubscribe(userInfo.getIsSubscribe())
//                .ipAddress()
//                .ipSource()
                .isDisable(userInfo.getIsDisable())
//                .browser()
//                .os()
                .lastLoginTime(new Date())
                .build();
    }
}
