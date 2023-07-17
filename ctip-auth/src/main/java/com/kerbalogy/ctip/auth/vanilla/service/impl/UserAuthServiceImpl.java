package com.kerbalogy.ctip.auth.vanilla.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kerbalogy.ctip.auth.vanilla.dto.UserLogoutStatusDTO;
import com.kerbalogy.ctip.auth.vanilla.entity.UserAuth;
import com.kerbalogy.ctip.auth.vanilla.entity.UserInfo;
import com.kerbalogy.ctip.auth.vanilla.entity.UserRole;
import com.kerbalogy.ctip.auth.vanilla.enums.RoleEnum;
import com.kerbalogy.ctip.auth.vanilla.mapper.UserAuthMapper;
import com.kerbalogy.ctip.auth.vanilla.mapper.UserInfoMapper;
import com.kerbalogy.ctip.auth.vanilla.mapper.UserRoleMapper;
import com.kerbalogy.ctip.auth.vanilla.service.UserAuthService;
import com.kerbalogy.ctip.auth.vanilla.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/10 22:20
 * @description
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void sendCode(String username) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO userVO) {
        if (!checkEmail(userVO.getUsername())) {
            throw new RuntimeException("邮箱格式不对!");
        }
        if (checkUser(userVO)) {
            throw new RuntimeException("邮箱已被注册！");
        }
        UserInfo userInfo = UserInfo.builder()
                .email(userVO.getUsername())
                .nickname("111")
                .avatar("111")
                .build();
        userInfoMapper.insert(userInfo);
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(userVO.getUsername())
                .password(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))
                .loginType(1)
                .build();
        userAuthMapper.insert(userAuth);
    }

    @Override
    public void updatePassword(UserVO userVO) {

    }

    @Override
    public UserLogoutStatusDTO logout() {
        return null;
    }

    public static boolean checkEmail(String username) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }

    private Boolean checkUser(UserVO user) {
//        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
//            throw new RuntimeException("验证码错误！");
//        }
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

}
