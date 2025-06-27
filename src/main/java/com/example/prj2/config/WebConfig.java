package com.example.prj2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 실제 폴더 경로
        String profileImagePath = "file:///" +
                                  System.getProperty("user.home").replace("\\", "/") +
                                  "/IdeaProjects/prj2/image/profile/";

        // URL로 접근할 경로 매핑
        registry.addResourceHandler("/images/profile/**")
                .addResourceLocations(profileImagePath);
    }
}
