package com.kerbalogy.ctip.infer.text2img.service.impl;

import com.kerbalogy.boot.kafka.KafkaProducer;
import com.kerbalogy.ctip.infer.text2img.dto.StableDiffusionParamDTO;
import com.kerbalogy.ctip.infer.text2img.service.TextToImgService;
import com.kerblogy.ctip.common.constant.KafkaTopics;
import com.kerblogy.ctip.common.kafka.KafkaMessage;
import com.kerblogy.ctip.common.kafka.KafkaMessageCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@Service
@Slf4j
public class TextToImgServiceImpl implements TextToImgService {

    @Resource
    KafkaProducer mainKafkaProducer;

    @Override
    public Boolean requestTextToImgStableDiffusion(String prompt) {
        return requestTextToImgStableDiffusion(StableDiffusionParamDTO.builder().prompt(prompt).build());
    }

    @Override
    public Boolean requestTextToImgStableDiffusion(StableDiffusionParamDTO stableDiffusionParamDTO) {
        try {
            mainKafkaProducer.sendMsg(KafkaTopics.TEXT_TO_IMG_EXPORT,
                    KafkaMessage.newMessage(KafkaMessageCode.PLAIN, stableDiffusionParamDTO));

            // TODO：落库 task

            return true;
        } catch (Exception e) {
            log.error("消息发送错误", e);
            return false;
        }
    }
}
