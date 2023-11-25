package com.xingchi.tornado.unique.provider.impl;

import com.xingchi.tornado.unique.common.IDProviderType;
import com.xingchi.tornado.unique.provider.IDProvider;

import java.time.Instant;
import java.util.concurrent.atomic.LongAdder;

/**
 * 简单累加器实现（累加器，借助于longAddr实现）
 * 时间戳(单位秒，32位) + 8位机器id + 累加器（24)
 * 预计每秒可产生 2^24 个id，使用该累加器机器ip不能相同
 *
 * @author xingchi
 * @date 2023/11/15 15:42
 */
public class SimpleProvider implements IDProvider<Long> {

    /**
     * 累加器
     */
    private final LongAdder adder = new LongAdder();

    /**
     * 机器id位数
     */
    private static final int MACHINE_ID_BIT = 8;

    /**
     * 时间戳位数
     */
    private static final int EPOCH_BIT = 32;

    /**
     * 序列号id位数
     */
    private static final int SEQUENCE_BIT = 24;

    /**
     * 机器id，集群环境下，该值不能相同否则可能产生id重复问题
     */
    private final int machineId;

    /**
     * 开始时间，单位s
     */
    private static final long TIME_EPOCH = 1675282963L;

    /**
     * 上一毫秒
     */
    private long lastTimestamp = -1L;

    public SimpleProvider(int machineId) {
        this.machineId = machineId;
    }

    @Override
    public synchronized Long nextId() {

        // 获取当前时间，并计算当前时间与起始时间的差值
        Instant now = Instant.now();
        long timestamp = now.getEpochSecond() - TIME_EPOCH;

        if (timestamp < 0) {
            throw new RuntimeException("时钟回拨，请调整系统时间！");
        }

        // 不再还在同一秒，进行重置
        if (lastTimestamp != timestamp) {
            adder.reset();
            lastTimestamp = timestamp;
        }

        adder.increment();
        return (timestamp << EPOCH_BIT) | ((long) machineId << (SEQUENCE_BIT | MACHINE_ID_BIT)) | adder.intValue();
    }

    @Override
    public boolean supports(IDProviderType type) {
        return IDProviderType.SIMPLE.equals(type);
    }

}
