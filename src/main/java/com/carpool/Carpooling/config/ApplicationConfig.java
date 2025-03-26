package com.carpool.Carpooling.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}
