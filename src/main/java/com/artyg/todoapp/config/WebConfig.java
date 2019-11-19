package com.artyg.todoapp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/api/todo/**").allowedOrigins(Globals.FRONTEND_URL).allowedMethods("*");
        registry.addMapping("/api/todo").allowedOrigins(Globals.FRONTEND_URL);
        registry.addMapping("/api/todos").allowedOrigins(Globals.FRONTEND_URL);
    }
}