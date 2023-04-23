package com.example.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 授权"/", "/home"，正常访问
        // 其他路径都必须经过身份验证
        // 如果登录完成，将重定向到之前请求的需要身份验证的页面
        http
                .authorizeHttpRequests((requests) -> requests
                                // TODO:新版本5.7.8得写里面
//                        .requestMatchers("/", "/home").permitAll()
                                .antMatchers("/", "/home").permitAll()
                                .anyRequest().authenticated()
                )
                // TODO: spring security 新版本5.7.8不能写外面,5.1.1和5.1.4都可以，但在pom.xml中不能直接指定版本，它是通过spring-boot-starter-security的依赖进来的，麻烦，没改动
//                .antMatchers("/").permitAll()
//                .antMatchers("/home").permitAll()

                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
