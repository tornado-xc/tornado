package com.xingchi.tornado.core.config;

import com.xingchi.tornado.core.context.SpringContextHolder;
import com.xingchi.tornado.core.lock.RedisLock;
import com.xingchi.tornado.core.plugins.IdAutoInterceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
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
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class GeneralConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单机
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase()); // 更多.set

        RedissonClient redissonClient = Redisson.create(config);
        RedisLock.init(redissonClient, false);
        return redissonClient;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return SpringContextHolder.getInstance();
    }

    @Bean
    @ConditionalOnMissingBean
    public IdAutoInterceptor idAutoInterceptor() {
        return new IdAutoInterceptor(true);
    }

}
