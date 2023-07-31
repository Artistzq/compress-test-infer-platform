package com.kerblogy.ctip.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-31
 * @description
 **/

public interface KafkaConsumer {

    void consume(ConsumerRecord<String, String> consumerRecord);

}
