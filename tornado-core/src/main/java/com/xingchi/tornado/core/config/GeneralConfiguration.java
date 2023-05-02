package com.xingchi.tornado.core.config;

import com.xingchi.tornado.core.context.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
