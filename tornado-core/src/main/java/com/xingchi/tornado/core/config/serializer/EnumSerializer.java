package com.xingchi.tornado.core.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xingchi.tornado.basic.BaseEnum;
import com.xingchi.tornado.core.config.anno.DataMask;
import com.xingchi.tornado.core.config.anno.JsonEnum;
import com.xingchi.tornado.core.config.datamask.DataMaskSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * 枚举值序列化处理
 *
 * @author xiaoya
 * @date 2023/6/16 15:33
 * @modified xiaoya
 */
public class EnumSerializer extends JsonSerializer<Integer> implements ContextualSerializer {

    private Class<? extends BaseEnum> type;

    public EnumSerializer(Class<? extends BaseEnum> type) {
        this.type = type;
    }

    @Override
    public void serialize(Integer integer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), Integer.class)) {
                JsonEnum jsonEnum = beanProperty.getAnnotation(JsonEnum.class);
                if (jsonEnum == null) {
                    jsonEnum = beanProperty.getContextAnnotation(JsonEnum.class);
                }
                if (jsonEnum != null) {
                    return new EnumSerializer(jsonEnum.value());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
