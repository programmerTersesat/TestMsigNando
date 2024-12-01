package com.test.msig.student.demo.config;

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
                registry.addMapping("/**") // Mengizinkan semua endpoint
                        .allowedOrigins("http://localhost:8080") // Sesuaikan dengan origin tempat file HTML Anda dibuka
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Metode yang diizinkan
                        .allowedHeaders("*"); // Header yang diizinkan
            }
        };
    }
}

