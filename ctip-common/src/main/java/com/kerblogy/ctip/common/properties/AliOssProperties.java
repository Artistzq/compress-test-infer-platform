package com.kerblogy.ctip.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName AliOssProperties
 * @Description
 * @Author zhucui
 * @Date 2023/8/1 10:57
 **/

@Component
@ConfigurationProperties(prefix = "ctip.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
