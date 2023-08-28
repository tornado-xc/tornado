package com.xingchi.tornado.unique.common;

import java.util.Arrays;

/**
 * id生成类型
 *
 * @author xingchi
 * @date 2023/3/10 19:57
 * @modified xingchi
 */
public enum IDProviderType {

    /**
     * id提供者类型
     */
    SNOWFLAKE,
    REDIS,
    UUID,
    SIMPLE,
    ;

    public static IDProviderType getInstance(String name) {
        return Arrays.stream(IDProviderType.values()).filter(item -> item.name().equalsIgnoreCase(name)).findFirst().orElse(IDProviderType.SNOWFLAKE);
    }

}
