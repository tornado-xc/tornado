package com.xingchi.tornado.core.config.datamask;

/**
 * 数据脱敏类型
 *
 * @author xingchi
 * @date 2023/2/24 22:30
 * @modified xingchi
 */
public enum DataMaskType {

    /**
     * 常见类型
     */
    NAME,
    EMAIL,
    PHONE,
    ID_CARD,


    /**
     * 自定义类型
     */
    CUSTOM,

}
