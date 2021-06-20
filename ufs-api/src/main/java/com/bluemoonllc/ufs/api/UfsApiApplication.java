package com.bluemoonllc.ufs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UfsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UfsApiApplication.class, args);
    }

}
