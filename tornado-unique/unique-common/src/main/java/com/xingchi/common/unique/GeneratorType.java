package com.xingchi.common.unique;

import java.util.Arrays;

/**
 * id生成类型
 *
 * @author xingchi
 * @date 2023/3/10 19:57
 * @modified xingchi
 */
public enum GeneratorType {

    SNOWFLAKE,
    REDIS,
    UUID,
    ;

    public static GeneratorType getInstance(String name) {
        return Arrays.stream(GeneratorType.values()).filter(item -> item.name().equalsIgnoreCase(name)).findFirst().orElse(GeneratorType.SNOWFLAKE);
    }

}
