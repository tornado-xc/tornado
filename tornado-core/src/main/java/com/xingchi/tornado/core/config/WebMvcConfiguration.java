package com.xingchi.tornado.core.config;

import com.xingchi.tornado.core.interceptor.DistributedIdInterceptor;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author xiaoya
 * @date 2023/6/19 17:50
 * @modified xiaoya
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Lazy
    @Resource
    private UniqueCodeClient uniqueCodeClient;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DistributedIdInterceptor(uniqueCodeClient));
    }

}
