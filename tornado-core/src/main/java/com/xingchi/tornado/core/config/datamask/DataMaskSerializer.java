package com.xingchi.tornado.core.config.datamask;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xingchi.tornado.core.config.anno.DataMask;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 序列化
 *
 * @author xingchi
 * @date 2023/2/24 22:32
 * @modified xingchi
 */
public class DataMaskSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private DataMaskType type;
    private int prefixNoMaskLen;
    private int suffixNoMaskLen;
    private String symbol;

    public DataMaskSerializer() {
    }

    public DataMaskSerializer(DataMaskType type, int prefixNoMaskLen, int suffixNoMaskLen, String symbol) {
        this.type = type;
        this.prefixNoMaskLen = prefixNoMaskLen;
        this.suffixNoMaskLen = suffixNoMaskLen;
        this.symbol = symbol;
    }

    @Override
    public void serialize(String origin, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (StringUtils.isNotBlank(origin) && type != null) {
            switch (type) {
                case CUSTOM:
                    jsonGenerator.writeString(DataMaskUtils.desValue(origin, prefixNoMaskLen, suffixNoMaskLen, symbol));
                    break;
                case NAME:
                    jsonGenerator.writeString(DataMaskUtils.hideChineseName(origin));
                    break;
                case ID_CARD:
                    jsonGenerator.writeString(DataMaskUtils.hideIDCard(origin));
                    break;
                case PHONE:
                    jsonGenerator.writeString(DataMaskUtils.hidePhone(origin));
                    break;
                case EMAIL:
                    jsonGenerator.writeString(DataMaskUtils.hideEmail(origin));
                    break;
                default:
                    throw new IllegalArgumentException("unknown privacy type enum " + type);
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                DataMask privacyEncrypt = beanProperty.getAnnotation(DataMask.class);
                if (privacyEncrypt == null) {
                    privacyEncrypt = beanProperty.getContextAnnotation(DataMask.class);
                }
                if (privacyEncrypt != null) {
                    return new DataMaskSerializer(privacyEncrypt.type(), privacyEncrypt.prefixNoMaskLen(), privacyEncrypt.suffixNoMaskLen(), privacyEncrypt.symbol());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }

}
