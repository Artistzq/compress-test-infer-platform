package com.kerblogy.ctip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yaozongqing@eastmoney.com
 * @description 服务源枚举类
 * @date 2023-07-06
 **/
@Getter
@AllArgsConstructor
public enum ServiceSourceEnum {

    SPRING(0, "spring"),

    PY_FASTAPI(1, "py-fastapi");

    private final int code;

    private final String source;

}
