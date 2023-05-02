package com.xingchi.tornado.core.config.serializer;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数字序列化方式
 *
 * @author xiaoya
 * @date 2023/1/13 9:42
 * @modified xiaoya
 */
public class NumberSerializerModule extends SimpleModule {

    public NumberSerializerModule() {
        super(PackageVersion.VERSION);

        // long类型使用String进行序列化
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(BigInteger.class, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);

    }

}
