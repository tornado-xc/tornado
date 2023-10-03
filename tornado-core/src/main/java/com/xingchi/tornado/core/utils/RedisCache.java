package com.xingchi.tornado.core.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingchi.tornado.core.lock.RedisLock;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * redis缓存工具类
 *
 * @author xingchi
 * @date 2023/7/2 13:43
 * @modified xingchi
 */
@Component
public class RedisCache {

    public static final Long CACHE_NULL_TTL = 2L;
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newSingleThreadExecutor();

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCache(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public <T> void set(String key, T data, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(data), time, unit);
    }

    public <T> void setWithLogicalExpire(String key, T data, Long time, TimeUnit unit) {
        RedisData<T> redisData = new RedisData<>();
        redisData.setData(data);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(redisData), time, unit);
    }

    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbCallBack, Long time, TimeUnit unit) {

        String key = keyPrefix + id;
        // 1. 查询redis
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(json)) {
            // 存在
            return JSONObject.parseObject(json, type);
        }

        // 是空串，则直接返回null
        if ("".equals(json)) {
            // 解决缓存穿透击穿问题
            return null;
        }

        // 2. 不存在则查询数据库将结果缓存
        R result = dbCallBack.apply(id);
        if (result == null) {
            // 缓存空值，说明数据库中不存在该值
            this.set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        this.set(key, result, time, unit);
        return result;
    }

    public <R, ID> R queryWithLogicalExpire(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {

        String key = keyPrefix + id;
        // 查询redis
        String json = stringRedisTemplate.opsForValue().get(key);

        // redis中没有则直接返回空
        if (!StringUtils.hasText(json)) {
            return null;
        }

        // 命中，需要先把json反序列化为对象
        RedisData<R> redisData = JSONObject.parseObject(json, new TypeReference<RedisData<R>>(){});
        R result = redisData.getData();
        LocalDateTime expireTime = redisData.getExpireTime();

        // 判断是否过期
        if(expireTime.isAfter(LocalDateTime.now())) {
            // 未过期，直接返回
            return result;
        }
        // 已过期，需要缓存重建 获取互斥锁
        String lockKey = keyPrefix + "lock:" + id;
        boolean isLock = RedisLock.tryLock(lockKey);
        // 6.2.判断是否获取锁成功
        if (isLock){
            // 6.3.成功，开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查询数据库
                    R newR = dbFallback.apply(id);
                    // 重建缓存
                    this.setWithLogicalExpire(key, newR, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    // 释放锁
                    RedisLock.unlock(lockKey);
                }
            });
        }
        // 返回过期的商铺信息
        return result;
    }

    public <R, ID> R queryWithMutex(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {

        // 从redis查询缓存
        String key = keyPrefix + id;
        String json = stringRedisTemplate.opsForValue().get(key);

        // 2.判断是否存在
        if (StringUtils.hasText(json)) {
            // 3.存在，直接返回
            return JSONObject.parseObject(json, type);
        }
        // 判断命中的是否是空值
        if ("".equals(json)) {
            // 返回一个错误信息
            return null;
        }

        // 实现缓存重建 获取互斥锁
        String lockKey = keyPrefix + "lock:" + id;
        R result = null;
        try {
            boolean isLock = tryLock(lockKey);
            // 4.2.判断是否获取成功
            if (!isLock) {
                // 4.3.获取锁失败，休眠并重试
                Thread.sleep(50);
                return queryWithMutex(keyPrefix, id, type, dbFallback, time, unit);
            }
            // 获取锁成功，根据id查询数据库
            result = dbFallback.apply(id);
            // 不存在，返回错误
            if (result == null) {
                // 将空值写入redis
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                // 返回错误信息
                return null;
            }
            // 存在，写入redis
            this.set(key, result, time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            // 释放锁
            unlock(lockKey);
        }
        // 返回
        return result;
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtils.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }


}
