package com.xingchi.tornado.unique.factory;

/**
 * id生成器
 *
 * @author xingchi
 * @date 2023/5/3 9:55
 * @modified xingchi
 */
@FunctionalInterface
public interface IDGGenerator<T> {

    T nextId();

}
