package com.xingchi.tornado.core.context;


import com.xingchi.tornado.basic.BaseParameter;

/**
 * 基础上下文对象，用于在同一个线程中进行数据共享(使用完成必须手动调用remove方法，以防止内存泄漏)
 *
 * @author xingchi
 * @date 2022/9/11 19:32
 * @modified xingchi
 */
public class BaseContextHolder {

    private static final ThreadLocal<BaseParameter> CONTEXT = ThreadLocal.withInitial(BaseParameter::init);

    /**
     * 在上下文中设置一个KV键值对
     *
     * @param key           属性名
     * @param value         属性值
     */
    public static void set(String key, Object value) {
        if (key == null && value == null) {
            throw new IllegalArgumentException("forbid in BaseContextHolder setting null key or null value");
        }
        CONTEXT.get().set(key, value);
    }

    /**
     * 从上下文对象中获取指定名称的值
     *
     * @param name          属性名
     * @param <T>           返回值类型参数
     * @return              指定属性名所对应的数据
     */
    public static <T> T get(String name) {
        return CONTEXT.get().get(name);
    }

    /**
     * 从上下文对象中获取指定名称的值
     *
     * @param name          属性名
     * @param <T>           返回值类型参数
     * @return              指定属性名所对应的数据
     */
    public static <T> T get(String name, Class<T> type) {
        return CONTEXT.get().get(name, type);
    }

    /**
     * 移除上下文对象，必须手动进行释放以防止内存泄漏问题，使用方式同ThreadLocal
     */
    public static void remove() {
        CONTEXT.remove();
    }

}
