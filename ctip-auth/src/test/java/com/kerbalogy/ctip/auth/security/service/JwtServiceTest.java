package com.kerbalogy.ctip.auth.security.service;

import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void createJWT() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrZXJiIiwic3ViIjoiXCIxXCIiLCJleHAiOjE2OTE2NjIwMDgsImlhdCI6MTY5MTY1ODQwOCwianRpIjoiYTQ4ZjY1OGMyZDNkNGRmNzgxZDU2ZTA3MDY5YjEzYjcifQ.a_TBfTKUeaMSGYnXwCq1jM8aeqeAmR9Pc7XPKdvPMHk";
        System.out.println(token);
        JWTClaimsSet claimsSet = jwtService.parseJWT(token);
        System.out.println(claimsSet);

    }

    @Test
    void valid() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrZXJiIiwic3ViIjoiXCIxXCIiLCJleHAiOjE2OTE2NjIwMDgsImlhdCI6MTY5MTY1ODQwOCwianRpIjoiYTQ4ZjY1OGMyZDNkNGRmNzgxZDU2ZTA3MDY5YjEzYjcifQ.a_TBfTKUeaMSGYnXwCq1jM8aeqeAmR9Pc7XPKdvPMHk";
        JWTClaimsSet claimsSet = jwtService.parseJWT(token);
        System.out.println(claimsSet);

        assert ! jwtService.expired(token);
    }
}