package com.xingchi.tornado.core.lock;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedisLock
{
    private static RedissonClient redissonClient;

    /**
     * 初始化
     *
     * @param redissonClient        redisson客户端
     * @param isRefresh             是否刷新，如果为true会使用传入的不为空就会刷新原有的值
     */
    public static void init(RedissonClient redissonClient, boolean isRefresh) {
        if (redissonClient != null && (isRefresh || RedisLock.redissonClient == null)) {
            RedisLock.redissonClient = redissonClient;
        }
    }

    /**
     * 获取锁
     *
     * @param lockKey 锁实例key
     * @return 锁信息
     */
    private static RLock getRLock(String lockKey)
    {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 加锁
     * 
     * @param lockKey 锁实例key
     * @return 锁信息
     */
    public static RLock lock(String lockKey)
    {
        RLock lock = getRLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 加锁
     * 
     * @param lockKey 锁实例key
     * @param leaseTime 上锁后自动释放锁时间
     * @return true=成功；false=失败
     */
    public static boolean tryLock(String lockKey, long leaseTime) {
        return tryLock(lockKey, 0, leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     * 
     * @param lockKey 锁实例key
     * @param leaseTime 上锁后自动释放锁时间
     * @param unit 时间颗粒度
     * @return true=加锁成功；false=加锁失败
     */
    public static boolean tryLock(String lockKey, long leaseTime, TimeUnit unit) {
        return tryLock(lockKey, 0, leaseTime, unit);
    }

    /**
     * 加锁
     * 
     * @param lockKey 锁实例key
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @param unit 时间颗粒度
     * @return true=加锁成功；false=加锁失败
     */
    public static boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock rLock = getRLock(lockKey);
        boolean tryLock = false;
        try
        {
            tryLock = rLock.tryLock(waitTime, leaseTime, unit);
        }
        catch (InterruptedException e)
        {
            return false;
        }
        return tryLock;
    }

    /**
     * 释放锁
     * 
     * @param lockKey 锁实例key
     */
    public static void unlock(String lockKey) {
        RLock lock = getRLock(lockKey);
        lock.unlock();
    }

    /**
     * 释放锁
     * 
     * @param lock 锁信息
     */
    public static void unlock(RLock lock) {
        lock.unlock();
    }
}