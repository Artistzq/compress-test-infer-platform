package com.kerbalogy.ctip.se4ai.compress.producer;

import com.kerblogy.ctip.common.kafka.KafkaMessage;
import com.kerblogy.ctip.common.kafka.KafkaProducer;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-31
 * @description
 **/
@Component
public class CompressKafkaProducer implements KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "compress";

    public <T> void send(KafkaMessage<T> message) {
        kafkaTemplate.send(TOPIC, JacksonUtil.to(message));
    }

}
