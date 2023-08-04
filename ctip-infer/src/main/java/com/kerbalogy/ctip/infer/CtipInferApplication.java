package com.kerbalogy.ctip.infer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {
        "com.kerbalogy.boot", "com.kerbalogy.ctip.infer"
})
@EnableFeignClients
public class CtipInferApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtipInferApplication.class, args);
    }
}
