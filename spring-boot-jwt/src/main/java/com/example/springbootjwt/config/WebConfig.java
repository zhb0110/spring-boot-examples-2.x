package com.example.springbootjwt.config;

import com.example.springbootjwt.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private TokenInterceptor tokenInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {

        // TODO:自己注入实现的过滤器
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }
}
