package com.kerbalogy.ctip.auth.vanilla.service;

import com.kerbalogy.ctip.auth.vanilla.vo.UserVO;

import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/10 22:20
 * @description
 */
public interface UserAuthService {

    void sendCode(String username);

    void register(UserVO userVO);

    void updatePassword(UserVO userVO);

}
