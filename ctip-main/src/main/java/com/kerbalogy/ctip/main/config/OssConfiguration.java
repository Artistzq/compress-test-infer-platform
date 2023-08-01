package com.kerbalogy.ctip.main.config;

import com.kerblogy.ctip.common.properties.AliOssProperties;
import com.kerblogy.ctip.common.util.oss.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AliOssConfig
 * @Description
 * @Author zhucui
 * @Date 2023/8/1 11:27
 **/
@Configuration
@Slf4j
public class OssConfiguration {
    /**
     * 当没有bean的时候再创建，工具类只需要一个就够了
     * @param properties 自动注入的属性
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties properties) {
        log.info("开始创建阿里云OSS工具类{}", properties);
        return new AliOssUtil(
                properties.getEndpoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecret(),
                properties.getBucketName()
        );
    }
}

