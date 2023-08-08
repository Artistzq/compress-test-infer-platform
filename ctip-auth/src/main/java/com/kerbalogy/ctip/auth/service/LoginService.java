package com.kerbalogy.ctip.auth.service;

import com.kerbalogy.ctip.auth.entity.LoginUser;

import java.util.Map;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
public interface LoginService {

    Map<String, String> login(LoginUser user);

}
