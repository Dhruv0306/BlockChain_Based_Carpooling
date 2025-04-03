package com.carpooling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public Map<String, String> healthCheck() {
        Map<String, String> status = new HashMap<>();
        
        try {
            // Test database connection
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            status.put("database", "connected");
            status.put("status", "up");
        } catch (Exception e) {
            status.put("database", "error: " + e.getMessage());
            status.put("status", "down");
        }
        
        return status;
    }

    @GetMapping("/db")
    public Map<String, Object> databaseStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // Test database connection
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            
            // Get database version
            String version = jdbcTemplate.queryForObject("SELECT version()", String.class);
            
            // Get database name
            String dbName = jdbcTemplate.queryForObject("SELECT current_database()", String.class);
            
            // Get current user
            String currentUser = jdbcTemplate.queryForObject("SELECT current_user", String.class);
            
            status.put("status", "connected");
            status.put("version", version);
            status.put("database", dbName);
            status.put("user", currentUser);
            status.put("error", null);
        } catch (Exception e) {
            status.put("status", "error");
            status.put("error", e.getMessage());
            status.put("version", null);
            status.put("database", null);
            status.put("user", null);
        }
        
        return status;
    }
} 