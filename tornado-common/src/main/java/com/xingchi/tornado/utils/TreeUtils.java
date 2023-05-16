package com.xingchi.tornado.utils;

import com.xingchi.tornado.tree.TreeNode;
import org.checkerframework.checker.units.qual.A;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形节点工具类
 *
 * @author xiaoya
 * @date 2023/5/15 15:58
 * @modified xiaoya
 */
public class TreeUtils {

    /**
     * 获取指定节点的父节点信息
     *
     * @param nodeId   指定节点
     * @param function 获取父节点函数
     * @param <T>      节点id
     * @param <M>      节点类型
     * @return 返回指定节点id的父节点信息
     */
    public static <T extends Serializable, M> M getParent(T nodeId, Function<T, M> function) {
        return function.apply(nodeId);
    }

    /**
     * 构建树形结构
     *
     * @param nodes 节点信息，平铺的
     * @param <M>   模型类型
     * @return 封装号的树形结构，树形的
     */
    public static <M extends TreeNode<? extends Serializable, M>> List<M> build(List<M> nodes) {

        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }

        Map<? extends Serializable, List<M>> parentNodeMappings = nodes.stream().collect(Collectors.groupingBy(TreeNode::getParentId));

        for (M node : nodes) {
            Serializable id = node.getId();
            if (parentNodeMappings.containsKey(id)) {
                node.setChildren(parentNodeMappings.get(id));
            }
        }


        return null;
    }

}
