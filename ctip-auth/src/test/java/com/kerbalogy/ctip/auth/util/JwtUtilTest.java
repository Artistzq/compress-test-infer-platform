package com.kerbalogy.ctip.auth.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void createJWT() throws Exception {
        String token = jwtUtil.createJWT("kerb");
        System.out.println(token);
        JWTClaimsSet claimsSet = jwtUtil.parseJWT(token);
        System.out.println(claimsSet);

    }
}