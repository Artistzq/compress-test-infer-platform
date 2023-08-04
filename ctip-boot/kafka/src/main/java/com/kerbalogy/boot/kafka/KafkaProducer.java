package com.kerbalogy.boot.kafka;

import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CompletableFuture;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-04
 * @description
 **/
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 同步、发送消息，一直等待发送成功返回结果
     *
     * @param topic kafka 话题
     * @param msg   消息
     */
    public Boolean sendMsg(String topic, Object msg) {
        String strMsg = JacksonUtil.to(msg);
        try {
            kafkaTemplate.send(topic, strMsg).get();
            LOGGER.info("发送kafka消息成功！topic={}, msg={}", topic, strMsg);
            return true;
        } catch (Exception ex) {
            LOGGER.error("发送kafka消息失败！topic={},msg={}", topic, strMsg, ex);
            return false;
        }
    }

    /**
     * 同步、发送消息，一直等待发送成功返回结果
     *
     * @param topic kafka 话题
     * @param key   相同key的消息会发送至相同的partition避免乱序
     * @param msg   消息
     */
    public Boolean sendMsg(String topic, String key, Object msg) {
        String strMsg = JacksonUtil.to(msg);
        try {
            kafkaTemplate.send(topic, key, strMsg).get();
            LOGGER.info("发送kafka消息成功！topic={}, key={}, msg={}", topic, key, strMsg);
            return true;
        } catch (Exception ex) {
            LOGGER.error("发送kafka消息失败！topic={}, key={}, msg={}", topic, key, strMsg, ex);
            return false;
        }
    }

    /**
     * 异步发送消息，吞吐量更高，但是如果发送错误需要进行回调处理
     *
     * @param topic    kafka topic
     * @param msg      kafka消息
     * @param callback ListenableFutureCallback回调，重写onSuccess，onFailure
     */
    public void asyncSendMsg(String topic, Object msg, CompletableFuture <? super SendResult<String, String>> callback) {
        String strMsg = JacksonUtil.to(msg);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, strMsg);
        future.thenAccept(callback::complete);
    }

    /**
     * 异步发送消息，吞吐量更高，但是如果发送错误需要进行回调处理
     *
     * @param topic    kafka topic
     * @param key      key
     * @param msg      kafka消息
     * @param callback ListenableFutureCallback回调，重写onSuccess，onFailure
     */
    public void asyncSendMsg(String topic, Object msg, String key, CompletableFuture<? super SendResult<String, String>> callback) {
        String strMsg = JacksonUtil.to(msg);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, strMsg);
        future.thenAccept(callback::complete);
    }

    @Resource
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}