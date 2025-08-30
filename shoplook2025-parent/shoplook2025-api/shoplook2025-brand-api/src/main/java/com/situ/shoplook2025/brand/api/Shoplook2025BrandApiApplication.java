package com.situ.shoplook2025.brand.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Shoplook2025BrandApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shoplook2025BrandApiApplication.class, args);
    }
}
