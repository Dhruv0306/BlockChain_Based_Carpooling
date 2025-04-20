package com.carpool.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource(Dotenv dotenv) {
        return DataSourceBuilder
            .create()
            .driverClassName("org.postgresql.Driver")
            .url(dotenv.get("DB_URL"))
            .username(dotenv.get("DB_USERNAME"))
            .password(dotenv.get("DB_PASSWORD"))
            .build();
    }
} 