package com.abcignitecms.config;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abcignitecms.model.GymClass;

@Configuration
public class AppConfig {

    @Bean
    public GymClass gymClass() {
        return new GymClass("Default Class", LocalDate.now(), LocalDate.now(), LocalTime.now(), 30, 20);
    }
}