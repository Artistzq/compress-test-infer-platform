package com.kerbalogy.ctip.infer.text2img.producer;

import com.kerblogy.ctip.common.constant.KafkaTopics;
import com.kerblogy.ctip.common.kafka.KafkaMessage;
import com.kerblogy.ctip.common.kafka.KafkaProducer;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/

@Component
public class TextToImgKafkaProducer implements KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public <T> void send(KafkaMessage<T> message) {
        kafkaTemplate.send(KafkaTopics.TEXT_TO_IMG_EXPORT, JacksonUtil.to(message));
    }

}
