package com.kerbalogy.ctip.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/9 12:07
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Oauth2TokenDTO {

    private String token;

    private String refreshToken;

    private String tokenHead;

    private int expiresIn;

}
