package com.hrd.somchbab.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class FileUploadConfiguration implements WebMvcConfigurer {
    @Value("${file.server.path}")
    String serverPath;
    @Value("${file.client.path}")
    String clientPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler( "/image/**").addResourceLocations("file:Controller/src/main/resources/static/image/profile/");
        registry.addResourceHandler( clientPath).addResourceLocations("file:"+serverPath);
    }
}
