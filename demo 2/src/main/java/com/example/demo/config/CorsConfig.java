package com.example.demo.config; // Update with your actual package name

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/github/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("POST", "GET", "PUT", "DELETE");
            }
        };
    }
}