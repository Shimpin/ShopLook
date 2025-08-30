package com.situ.shoplook2025.login.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Shoplook2025LoginApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shoplook2025LoginApiApplication.class, args);
    }

}
