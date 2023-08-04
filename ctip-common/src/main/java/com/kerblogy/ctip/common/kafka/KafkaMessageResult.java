package com.kerblogy.ctip.common.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-04
 * @description
 **/
@Getter
@AllArgsConstructor
public enum KafkaMessageResult {

    SEND_SUCCESS("Kafka消息发送成功"),

    SEND_FAIL("Kafka消息发送失败");

    private final String msg;

}
