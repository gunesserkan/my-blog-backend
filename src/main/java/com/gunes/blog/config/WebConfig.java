package com.gunes.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","http://192.168.1.36:5173") // Sadece güvenilir origin'lere izin verin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İhtiyaç duyduğunuz yöntemlere izin verin
                .allowedHeaders("Authorization", "Content-Type") // Sadece gerekli başlıkları belirtin
                .allowCredentials(true); // Kimlik doğrulama bilgilerini paylaşmak istiyorsanız true yapın
    }
}

