package com.kh.team4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; //view 에서 접근할 경로
    private String savePath = "/file:///C:/projectFiles/"; //실제 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")    // 이곳으로 부터 오는 값 허용
                .allowedMethods("GET", "POST")  // 허용할 HTTP Method
                .allowCredentials(true)   // 쿠키허용?
                .maxAge(3000);   // 미리보낸 찌라시 목숨연장?
    }
}
