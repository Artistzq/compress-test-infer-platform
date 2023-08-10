package com.kerbalogy.ctip.auth.security.service;

import com.kerblogy.ctip.common.util.json.JacksonUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
@Component
public class JwtService {

    @Value("${jwt.ttl}")
    public Long JWT_TTL = 60 * 60 * 1000L; // 60 * 60 * 1000 (1 hour)

    @Value("${jwt.key}")
    public String JWT_KEY = "jwt-key";

    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String createJWT(String subject) {
        return createJWT(subject, JWT_TTL);
    }

    public String createJWT(String subject, Long ttlMillis) {
        return createJWT(getUUID(), subject, ttlMillis);
    }

    public String createJWT(String id, Object object, Long ttlMillis) {
        SecretKey secretKey = generalKey();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(id)
                .subject(JacksonUtil.to(object))
                .issuer("ctip")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + ttlMillis))
                .build();

        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException("创建JWT错误", e);
        }

        return jwsObject.serialize();
    }

    public JWTClaimsSet parseJWT(String jwt){
        SecretKey secretKey = generalKey();
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(jwt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            if (jwsObject.verify(new MACVerifier(secretKey))) {
                return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            }
            throw new RuntimeException("token非法");
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T parseJwtSubject(String token, Class<T> clazz) {
        JWTClaimsSet jwtClaimsSet = parseJWT(token);
        return JacksonUtil.from(jwtClaimsSet.getSubject(), clazz);
    }

    public boolean valid(String jwt) {
        JWTClaimsSet jwtClaimsSet = parseJWT(jwt);
        Date expirationTime = jwtClaimsSet.getExpirationTime();
        return !expirationTime.before(new Date());
    }

    public SecretKey generalKey() {
        byte[] keyBytes = JWT_KEY.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            keyBytes = sha.digest(keyBytes);
            keyBytes = Base64.getEncoder().encode(keyBytes);
            return new SecretKeySpec(keyBytes, "HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

