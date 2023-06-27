package com.kerbalogy.ctip.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CtipMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtipMainApplication.class, args);
    }

}
