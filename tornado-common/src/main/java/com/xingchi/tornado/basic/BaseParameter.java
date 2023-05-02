package com.xingchi.tornado.basic;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * 基础参数
 *
 * @author xingchi
 * @date 2022/9/11 20:46
 * @modified xingchi
 */
@SuppressWarnings("unchecked")
public class BaseParameter extends LinkedCaseInsensitiveMap<Object> {

    private BaseParameter() {
        super();
    }

    /**
     * 初始化
     *
     * @return      BaseParameter
     */
    public static BaseParameter init() {
        return new BaseParameter();
    }

    /**
     * 设置一个参数
     *
     * @param name          属性名
     * @param value         属性值
     * @return              调用者
     */
    public BaseParameter set(String name, Object value) {
        super.put(name, value);
        return this;
    }

    /**
     * 批量设置参数
     *
     * @param parameters    参数集合
     * @return              调用者
     */
    public BaseParameter set(Map<String, Object> parameters) {
        super.putAll(parameters);
        return this;
    }

    /**
     * 获取指定key所对应的参数值
     *
     * @param name          参数名
     * @param <T>           参数类型
     * @return              参数对应的值
     */
    public <T> T get(String name) {
        return this.get(name, (T) null);
    }

    /**
     * 获取指定name所对应的值，如果不存在则使用默认值代替
     *
     * @param name          参数名
     * @param defaultValue  默认值
     * @param <T>           返回值类型
     * @return              name所对应的值或默认值
     */
    public <T> T get(String name, T defaultValue) {
        return super.get(name) == null ? defaultValue : (T) super.get(name);
    }

    /**
     * 获取指定key，指定类型的值
     *
     * @param name          参数名
     * @param type          返回值类型
     * @param <T>           返回值泛型类型
     * @return              目标类型的对象
     */
    public <T> T get(String name, Class<T> type) {
        Object target = super.get(name);
        if (target != null) {
            Assert.isTrue(type.isInstance(target), String.format("'%s' is not an instance of '%s'", target, type.getName()));
            return (T) target;
        }
        return null;
    }

    /**
     * 获取指定key，指定类型的值
     *
     * @param name          参数名
     * @param type          返回值类型
     * @param <T>           返回值泛型类型
     * @return              目标类型的对象
     */
    public <T> T get(String name, Class<T> type, T defaultValue) {
        T result = this.get(name, type);
        return result == null ? defaultValue : result;
    }

    public static BaseParameter objToParameter(Object obj) {
        BaseParameter parameter = BaseParameter.init();
        Class<?> type = obj.getClass();
        ReflectionUtils.doWithFields(type, field -> {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(obj);
            parameter.set(name, value);
        });
        return parameter;
    }

    /**
     * clone方法
     *
     * @return              深拷贝对象
     */
    @NonNull
    @Override
    public BaseParameter clone() {
        BaseParameter baseParameter = new BaseParameter();
        return baseParameter.set(this);
    }
}
