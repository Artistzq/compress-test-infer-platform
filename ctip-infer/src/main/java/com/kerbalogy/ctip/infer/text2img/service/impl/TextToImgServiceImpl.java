package com.kerbalogy.ctip.infer.text2img.service.impl;

import com.kerbalogy.ctip.infer.text2img.dto.StableDiffusionParamDTO;
import com.kerbalogy.ctip.infer.text2img.producer.TextToImgKafkaProducer;
import com.kerbalogy.ctip.infer.text2img.service.TextToImgService;
import com.kerblogy.ctip.common.constant.KafkaTopics;
import com.kerblogy.ctip.common.kafka.KafkaMessage;
import com.kerblogy.ctip.common.kafka.KafkaMessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@Service
@Slf4j
public class TextToImgServiceImpl implements TextToImgService {

    @Autowired
    TextToImgKafkaProducer textToImgKafkaProducer;

    @Override
    public Boolean requestTextToImgStableDiffusion(String prompt) {
        return requestTextToImgStableDiffusion(StableDiffusionParamDTO.builder().prompt(prompt).build());
    }

    @Override
    public Boolean requestTextToImgStableDiffusion(StableDiffusionParamDTO stableDiffusionParamDTO) {
        try {
            textToImgKafkaProducer.send(KafkaMessage.newMessage(KafkaMessageCode.PLAIN, stableDiffusionParamDTO));

            // TODO：落库 task

            return true;
        } catch (Exception e) {
            log.error("消息发送错误", e);
            return false;
        }
    }
}
