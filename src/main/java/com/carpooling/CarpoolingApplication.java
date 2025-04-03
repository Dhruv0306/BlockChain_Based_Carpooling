package com.carpooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CarpoolingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarpoolingApplication.class, args);
    }
} 