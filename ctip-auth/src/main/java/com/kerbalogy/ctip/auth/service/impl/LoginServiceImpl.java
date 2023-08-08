package com.kerbalogy.ctip.auth.service.impl;

import com.kerbalogy.ctip.auth.entity.LoginUser;
import com.kerbalogy.ctip.auth.service.LoginService;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import com.kerbalogy.ctip.auth.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-08
 * @description
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisCache redisCache;

    @Override
    public Map<String, String> login(LoginUser user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getId().toString();
        String jwt = jwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token", jwt);
        return map;
    }
}
