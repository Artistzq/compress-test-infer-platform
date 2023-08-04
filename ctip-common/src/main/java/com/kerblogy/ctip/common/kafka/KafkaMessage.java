package com.kerblogy.ctip.common.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-31
 * @description kafka消息格式
 **/
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class KafkaMessage <T> {

    private String message;

    private int code;

    private T data;

    private Date time;

    private String messageId;

    private static <T> KafkaMessage<T> init(String message, int code, T data, Date time) {
        return KafkaMessage.<T>builder()
                .message(message)
                .code(code)
                .data(data)
                .time(time)
                .messageId(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
    }

    public static <T> KafkaMessage<T> newMessage(String message, int code, T data) {
        return init(message, code, data, new Date());
    }

    public static <T> KafkaMessage<T> newMessage(KafkaMessageCode kafkaMessageCode, T data) {
        return init(kafkaMessageCode.getMsg(), kafkaMessageCode.getCode(), data, new Date());
    }
}
