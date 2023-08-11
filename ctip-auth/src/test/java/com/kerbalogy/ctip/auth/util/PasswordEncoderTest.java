package com.kerbalogy.ctip.auth.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-10
 * @description
 **/
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void test() {

//        assert  passwordEncoder.matches("123456", "$10$JXwxyllXqteRptsmDUHMZ.QRfC.gbclr8XL6yRY2yI7yXa9B40ji.");
        System.out.println(passwordEncoder.encode("123"));
    }

}
