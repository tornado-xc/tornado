package com.xingchi.unique.provider.impl;

import com.xingchi.common.unique.IDProviderType;
import com.xingchi.unique.provider.IDProvider;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Redis生成器
 *
 * @author xingchi
 * @date 2023/5/3 10:18
 * @modified xingchi
 */
public class RedisProvider implements IDProvider<Long> {

    /**
     * StringRedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    private final String businessPrefix;

    public RedisProvider(StringRedisTemplate stringRedisTemplate, String businessPrefix) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.businessPrefix = businessPrefix;
    }

    /**
     * 开始时间，单位s
     */
    private static final long TIME_EPOCH = 1675282963L;

    /**
     * 序列号位数
     */
    private static final int SEQUENCE_BITS = 32;

    /**
     * key格式
     */
    private static final String FORMAT = "yyyy:MM:dd";

    @Override
    @SuppressWarnings({"all"})
    public Long nextId() {
        // 获取当前时间，并计算当前时间与起始时间的差值
        LocalDateTime now = LocalDateTime.now();
        long currentTime = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = currentTime - TIME_EPOCH;

        if (timestamp < 0) {
            throw new RuntimeException("时钟回拨，请调整系统时间！");
        }

        // 根据时间进行区分
        String date = now.format(DateTimeFormatter.ofPattern(FORMAT));
        long count = stringRedisTemplate.opsForValue().increment("id:" + businessPrefix + ":" + date);
        return timestamp << SEQUENCE_BITS | count;
    }

    @Override
    public boolean supports(IDProviderType type) {
        return IDProviderType.REDIS.equals(type);
    }

}
