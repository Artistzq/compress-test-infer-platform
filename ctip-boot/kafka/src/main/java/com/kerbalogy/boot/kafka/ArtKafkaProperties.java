package com.kerbalogy.boot.kafka;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-04
 * @description
 **/
@ConfigurationProperties(prefix = "art.spring.kafka")
public class ArtKafkaProperties {

    private String defaultConfig = "default";

    private Map<String, KafkaProperties> config = new HashMap<>();

    public String getDefaultConfig() {
        return this.defaultConfig;
    }

    public void setDefaultConfig(String defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    public Map<String, KafkaProperties> getConfig() {
        return this.config;
    }

    public void setConfig(Map<String, KafkaProperties> config) {
        this.config = config;
    }
}
