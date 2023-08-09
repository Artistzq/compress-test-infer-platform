package com.kerbalogy.ctip.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
public class OldRole implements GrantedAuthority{

    private Integer id;

    private String roleName;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName;
    }
}
