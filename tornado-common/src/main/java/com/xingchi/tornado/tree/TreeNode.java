package com.xingchi.tornado.tree;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 树形节点接口
 *
 * @param <T> id类型
 * @param <M> 模型
 * @author xiaoya
 * @date 2023/5/15 15:33
 * @modified xiaoya
 */
public interface TreeNode<T extends Serializable, M> {

    /**
     * 获取节点id
     *
     * @return 节点id
     */
    T getId();

    /**
     * 获取父节点id
     *
     * @return 父节点id
     */
    T getParentId();

    /**
     * 设置父节点id
     *
     * @param parentId 父节点id
     */
    void setParentId(T parentId);

    /**
     * 获取所有子节点
     *
     * @return 子节点
     */
    List<M> getChildren();

    /**
     * 设置子节点
     *
     * @param children 子节点信息
     */
    void setChildren(List<M> children);

    /**
     * 初始化子节点
     */
    default void initChildren() {
        if (getChildren() == null) {
            this.setChildren(Lists.newArrayList());
        }
    }

    void setLeaf(boolean isLeaf);

    /**
     * 判断当前节点是否是叶子节点
     *
     * @return      是否是叶子节点
     */
    boolean isLeaf();

}
