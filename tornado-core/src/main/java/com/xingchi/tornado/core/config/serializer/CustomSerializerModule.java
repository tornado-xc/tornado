package com.xingchi.tornado.core.config.serializer;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.xingchi.tornado.constant.DateTimeFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间序列化器
 *
 * @author xingchi
 * @date 2022/10/31 22:24
 * @modified xingchi
 */
public class CustomSerializerModule extends SimpleModule {

    public CustomSerializerModule() {
        super(PackageVersion.VERSION);
        // 序列化，Long类型序列化
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_TIME_FORMAT)));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        this.addSerializer(Instant.class, InstantSerializer.INSTANCE);

        // 反序列化
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_TIME_FORMAT)));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);


        // long类型使用String进行序列化
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(BigInteger.class, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
    }

}
