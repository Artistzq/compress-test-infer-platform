package com.kerbalogy.ctip.auth.vanilla.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-11
 * @description
 **/

@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(1, "管理员", "admin"),

    USER(2, "用户", "user"),

    TEST(3, "测试", "test"),

    VISITOR(4, "游客", "visitor");

    private final Integer roleId;

    private final String name;

    private final String label;

}
