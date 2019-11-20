package com.artyg.noteapp.config;

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
        registry.addMapping("/api/note/**").allowedOrigins(Globals.FRONTEND_URL).allowedMethods("*");
        registry.addMapping("/api/note").allowedOrigins(Globals.FRONTEND_URL);
        registry.addMapping("/api/notes").allowedOrigins(Globals.FRONTEND_URL);
    }
}