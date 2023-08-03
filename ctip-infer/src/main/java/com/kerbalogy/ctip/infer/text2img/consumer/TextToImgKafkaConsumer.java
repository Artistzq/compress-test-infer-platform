package com.kerbalogy.ctip.infer.text2img.consumer;

import com.kerblogy.ctip.common.kafka.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-02
 * @description
 **/
@Component
public class TextToImgKafkaConsumer implements KafkaConsumer {
    @Override
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        // TODO: 结果落库
    }
}
