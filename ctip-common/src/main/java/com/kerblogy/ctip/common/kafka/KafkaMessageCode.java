package com.kerblogy.ctip.common.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-31
 * @description
 **/
@Getter
@AllArgsConstructor
public enum KafkaMessageCode {

    PLAIN("普通信息", 0);

    private final String msg;

    private final int code;
}
