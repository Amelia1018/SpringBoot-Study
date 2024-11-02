package com.hxmeet.demospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBootApplication.class, args);
    }

}
