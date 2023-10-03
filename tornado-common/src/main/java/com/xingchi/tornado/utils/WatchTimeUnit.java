package com.xingchi.tornado.utils;

import java.util.concurrent.TimeUnit;

/**
 * StopWatch监控所支持的单位类型，用于限定其单位
 *
 * @author xiaoya
 * @date 2023/9/15
 */
public enum WatchTimeUnit {

    NANOSECONDS(TimeUnit.NANOSECONDS),
    MILLISECONDS(TimeUnit.MILLISECONDS),
    MICROSECONDS(TimeUnit.MICROSECONDS),
    SECONDS(TimeUnit.SECONDS),
    ;

    private final TimeUnit supportUnit;

    WatchTimeUnit(TimeUnit supportUnit) {
        this.supportUnit = supportUnit;
    }

    public TimeUnit getSupportUnit() {
        return supportUnit;
    }
}
