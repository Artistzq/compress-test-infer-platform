package com.kerbalogy.ctip.auth.controller;

import com.kerbalogy.ctip.auth.entity.LoginUser;
import com.kerbalogy.ctip.auth.service.LoginService;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public JsonResultVO<?> login(@RequestBody LoginUser user) {
        return JsonResultVO.success(loginService.login(user));
    }

}
