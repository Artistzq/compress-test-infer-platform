package com.kerbalogy.ctip.se4ai.compress.consumer;

import com.kerblogy.ctip.common.kafka.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-31
 * @description
 **/

public class CompressKafkaConsumer implements KafkaConsumer {

    @Override
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        // 任务结果落库

        // 响应异步请求
    }

}
