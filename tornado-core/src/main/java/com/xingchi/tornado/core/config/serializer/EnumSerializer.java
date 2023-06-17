package com.xingchi.tornado.core.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xingchi.tornado.basic.BaseEnum;
import com.xingchi.tornado.core.config.anno.JsonEnum;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 枚举值序列化处理
 *
 * @author xiaoya
 * @date 2023/6/16 15:33
 * @modified xiaoya
 */
public class EnumSerializer extends JsonSerializer<Integer> implements ContextualSerializer {

    private Class<? extends BaseEnum> type;
    private String fieldName;

    public EnumSerializer() {
    }

    public EnumSerializer(Class<? extends BaseEnum> type, String fieldName) {
        this.type = type;
        this.fieldName = fieldName;
    }

    @Override
    public void serialize(Integer source, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 是枚举并且继承实现BaseEnum接口
        if (source != null) {
            if (type != null && BaseEnum.class.isAssignableFrom(type) && Enum.class.isAssignableFrom(type)) {
                BaseEnum[] enums = type.getEnumConstants();
                Optional<BaseEnum> optional = Arrays.stream(enums).filter(item -> source.equals(item.code())).findFirst();
                if (optional.isPresent()) {
                    BaseEnum baseEnum = optional.get();
                    jsonGenerator.writeNumber(source);
                    jsonGenerator.writeStringField(fieldName + "Desc", baseEnum.description());
                }
            } else {
                jsonGenerator.writeNumber(source);
            }
        }
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
                    return new EnumSerializer(jsonEnum.value(), beanProperty.getName());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
