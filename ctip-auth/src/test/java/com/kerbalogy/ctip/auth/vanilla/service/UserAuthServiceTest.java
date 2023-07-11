package com.kerbalogy.ctip.auth.vanilla.service;

import com.kerbalogy.ctip.auth.vanilla.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAuthServiceTest {

    @Autowired
    UserAuthService userAuthService;

    @Test
    void register() {

        userAuthService.register(
                UserVO.builder()
                        .code("123")
                        .username("yaozongqing@email.com")
                        .password("123")
                        .build()
        );

    }
}