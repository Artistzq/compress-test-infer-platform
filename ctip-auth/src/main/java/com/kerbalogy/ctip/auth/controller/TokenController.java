package com.kerbalogy.ctip.auth.controller;

import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-16
 * @description
 **/
@RestController
@RequestMapping("/api/auth/token")
public class TokenController {

    @Autowired
    JwtService jwtService;

    @PostMapping("/refresh")
    public JsonResultVO<?> refresh(@PathParam("username") String username) {
        String newAccessToken = jwtService.createJWT(username, 30 * 1000L);
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", newAccessToken);
        return JsonResultVO.success(map);
    }

}
