package com.example.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 *
 * @author HQY
 * @since 2022-4-7
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 解决跨域请求 方式 1，通过 filter 方式实现 已删除
     * 解决跨域请求 方式 2，通过 servlet 方式实现
     *
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .exposedHeaders("Authorization")
                .maxAge(3600);
    }
}