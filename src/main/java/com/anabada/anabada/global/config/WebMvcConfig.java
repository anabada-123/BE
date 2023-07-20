package com.anabada.anabada.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://fe-anabadastore.vercel.app")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }

}
