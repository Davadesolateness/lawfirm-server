package com.lawfirm.lawfirmserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${image.storage.path:./images}")
    private String imageStoragePath;

    /**
     * 配置静态资源映射
     */
   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置图片存储目录映射为可访问的URL
        String imagePath = "file:" + new File(imageStoragePath).getAbsolutePath() + File.separator;
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagePath);
                
        // 添加其他静态资源配置（如果需要）
    }*/
} 