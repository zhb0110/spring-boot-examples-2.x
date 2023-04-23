package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 重点要实现WebMvcConfigurer
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home"); // /home指向home
        registry.addViewController("/").setViewName("home"); // /指向home
        registry.addViewController("/hello").setViewName("hello"); // /hello指向hello
        registry.addViewController("/login").setViewName("login"); // /login指向login
    }
}
