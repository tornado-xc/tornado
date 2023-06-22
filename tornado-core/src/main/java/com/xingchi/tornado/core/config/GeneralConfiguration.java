package com.xingchi.tornado.core.config;

import com.xingchi.tornado.core.context.SpringContextHolder;
import com.xingchi.tornado.core.interceptor.DistributedIdInterceptor;
import com.xingchi.tornado.core.plugins.IdAutoInterceptor;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 通用配置
 *
 * @author xingchi
 * @date 2023/5/1 21:47
 * @modified xingchi
 */
@Configuration
public class GeneralConfiguration {

    @Bean
    public SpringContextHolder springContextHolder() {
        return SpringContextHolder.getInstance();
    }

    @Bean
    public IdAutoInterceptor idAutoInterceptor() {
        return new IdAutoInterceptor(true);
    }

}
