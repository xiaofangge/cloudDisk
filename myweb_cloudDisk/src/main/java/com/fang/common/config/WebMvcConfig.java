package com.fang.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 川川
 * @date 2022-02-25 9:23
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                     // 设置允许跨域的路由
                .allowCredentials(true)                             // 是否发送Cookie
                .allowedOriginPatterns("*")                         // 设置允许跨域的域名
                .allowedMethods("GET", "POST", "DELETE", "PUT")     // 设置允许的方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
