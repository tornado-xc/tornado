package com.xingchi.tornado.unique.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 唯一标识配置信息
 *
 * @author xingchi
 * @date 2023/3/10 19:08
 * @modified xingchi
 */
@Data
@ConfigurationProperties(prefix = "unique.code")
public class UniqueProperties {

    private Snowflake snowflake = new Snowflake();
    private RedisId redisId = new RedisId();

    @Data
    public static class Snowflake {
        private Long workId = 1L;
        private Long datacenterId = 1L;
    }

    @Data
    public static class RedisId {
        private String businessPrefix = "default";
    }

}
