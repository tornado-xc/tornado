package com.xingchi.tornado.core.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * redis逻辑过期数据载体
 *
 * @author xingchi
 * @date 2023/7/2 14:50
 * @modified xingchi
 */
@Data
public class RedisData<T> {

    private LocalDateTime expireTime;
    private T data;

}
