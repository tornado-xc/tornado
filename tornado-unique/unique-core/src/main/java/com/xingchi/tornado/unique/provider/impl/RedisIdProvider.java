package com.xingchi.tornado.unique.provider.impl;

import com.xingchi.tornado.unique.common.IDProviderType;
import com.xingchi.tornado.unique.provider.IDProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Redis生成器
 *
 * @author xingchi
 * @date 2023/5/3 10:18
 * @modified xingchi
 */
public class RedisIdProvider implements IDProvider<Long> {

    /**
     * StringRedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    private final String businessPrefix;
    private final Integer step;

    private static final DefaultRedisScript<List> BATCH_ID_SCRIPT;

    static {
        BATCH_ID_SCRIPT = new DefaultRedisScript<>();
        BATCH_ID_SCRIPT.setScriptSource(new ResourceScriptSource(new ClassPathResource("batchIds.lua")));
        BATCH_ID_SCRIPT.setResultType(List.class);
    }

    public RedisIdProvider(StringRedisTemplate stringRedisTemplate,String businessPrefix, Integer step) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.businessPrefix = businessPrefix;
        this.step = step;
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
        long timestamp = now.toEpochSecond(ZoneOffset.UTC) - TIME_EPOCH;

        if (timestamp < 0) {
            throw new RuntimeException("时钟回拨，请调整系统时间！");
        }

        // 根据时间进行区分
        String date = now.format(DateTimeFormatter.ofPattern(FORMAT));
        long count = stringRedisTemplate.opsForValue().increment("id:" + businessPrefix + ":" + date, step);
        return timestamp << SEQUENCE_BITS | count;
    }

    @Override
    public boolean supports(IDProviderType type) {
        return IDProviderType.REDIS.equals(type);
    }

    /**
     * 批量获取id，redis为了提升效率，使用lua脚本获取
     *
     * @param count 获取个数
     * @return      redisid
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Long> nextIds(Integer count) {

        // 获取当前时间，并计算当前时间与起始时间的差值
        LocalDateTime now = LocalDateTime.now();
        long timestamp = now.toEpochSecond(ZoneOffset.UTC) - TIME_EPOCH;
        if (timestamp < 0) {
            throw new RuntimeException("时钟回拨，请调整系统时间！");
        }

        String date = now.format(DateTimeFormatter.ofPattern(FORMAT));

        List<Long> ids = stringRedisTemplate.execute(BATCH_ID_SCRIPT,
                Collections.singletonList("id:" + businessPrefix + ":" + date),
                String.valueOf(step), String.valueOf(count));
        if (CollectionUtils.isEmpty(ids)) {
            ids = Collections.emptyList();
        }
        return ids.stream().map(item -> timestamp << SEQUENCE_BITS | item).collect(Collectors.toList());
    }
}
