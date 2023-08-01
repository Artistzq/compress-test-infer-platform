package com.kerbalogy.ctip.infer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CtipInferApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtipInferApplication.class, args);
    }

}
