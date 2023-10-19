package com.xingchi.tornado.core.utils;

import com.xingchi.tornado.core.context.SpringContextHolder;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;

/**
 * 布隆过滤器
 *
 * @author xingchi
 * @date 2023/10/19 14:33
 * @modified xingchi
 */
public class BloomFilterUtils {

    private static RedissonClient redissonClient;

    private static final Long DEFAULT_EXPECTED_INSERTIONS = 10000L;

    private static final Double DEFAULT_FALSE_PROBABILITY = 0.05;

    public static void setRedissonClient(RedissonClient client) {
        redissonClient = client;
        if (redissonClient == null) {
            redissonClient = SpringContextHolder.getBean(RedissonClient.class);
        }
    }

    public static void addFilter(String filterName, String key) {
        addFilter(filterName, key, DEFAULT_EXPECTED_INSERTIONS, DEFAULT_FALSE_PROBABILITY);
    }

    public static void addFilter(String filterName, String key, Long expectedInsertions, Double falseProbability) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(expectedInsertions, falseProbability);
        bloomFilter.add(key);
    }

    public static boolean mightContain(String filterName, String key) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.contains(key);
    }

}
