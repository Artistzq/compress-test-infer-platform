package com.kerbalogy.ctip.auth.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@AllArgsConstructor
@Getter
public enum RoleTypeEnum {

    SUPER_ADMIN("SUPER_ADMIN", "超级管理员"),

    BUSINESS("BUSINESS", "业务管理员");

    final String code;

    final String name;


}
