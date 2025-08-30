package com.situ.shoplook2025.good.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Shoplook2025GoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shoplook2025GoodApiApplication.class, args);
    }

}
