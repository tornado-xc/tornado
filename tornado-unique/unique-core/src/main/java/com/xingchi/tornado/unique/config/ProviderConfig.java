package com.xingchi.tornado.unique.config;

import com.xingchi.tornado.unique.provider.impl.RedisProvider;
import com.xingchi.tornado.unique.provider.impl.SnowflakeProvider;
import com.xingchi.tornado.unique.provider.impl.UUIDProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 生成工厂配置
 *
 * @author xingchi
 * @date 2023/3/10 18:54
 * @modified xingchi
 */
@Configuration
@EnableConfigurationProperties({UniqueProperties.class})
public class ProviderConfig {

    @Bean
    @DependsOn("stringRedisTemplate")
    @ConditionalOnMissingBean
    public RedisProvider redisIdFactory(StringRedisTemplate stringRedisTemplate, UniqueProperties uniqueProperties) {
        UniqueProperties.RedisId redisId = uniqueProperties.getRedisId();
        Integer step = redisId.getStep();
        if (step == null || step <= 0) {
            step = 1;
        }
        return new RedisProvider(stringRedisTemplate, StringUtils.isNotBlank(redisId.getBusinessPrefix()) ? redisId.getBusinessPrefix() : "default", step);
    }

    @Bean
    @ConditionalOnMissingBean
    public UUIDProvider uuidFactory() {
        return new UUIDProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeProvider snowflakeFactory(UniqueProperties uniqueProperties) {
        UniqueProperties.Snowflake snowflake = uniqueProperties.getSnowflake();
        return new SnowflakeProvider(snowflake.getWorkId(), snowflake.getDatacenterId());
    }

}
