package com.xingchi.tornado.utils;

import com.xingchi.tornado.tree.TreeNode;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
     * 将List构建为树形结构，时间复杂度O(n^2)
     *
     * @param nodes 节点信息，平铺的
     * @param <M>   模型类型
     * @return 封装号的树形结构，树形的
     */
    public static <M extends TreeNode<? extends Serializable, M>> List<M> build(List<M> nodes) {

        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }

        // 记录自己是自己的父节点的id集合
        List<Serializable> selfIdEqSelfParent = new ArrayList<>();
        // 为每一个节点找到子节点集合
        for (M parent : nodes) {
            Serializable id = parent.getId();
            for (M children : nodes) {
                if (parent != children) {
                    // parent != children 这个来判断自己的孩子不允许是自己，因为有时候，根节点的parent会被设置成为自己
                    if (id.equals(children.getParentId())) {
                        parent.initChildren();
                        parent.getChildren().add(children);
                    }
                } else if (id.equals(parent.getParentId())) {
                    selfIdEqSelfParent.add(id);
                }
            }
        }

        // 找出根节点集合
        List<M> trees = new ArrayList<>();
        Set<Serializable> allIds = nodes.stream().map(TreeNode::getId).collect(Collectors.toSet());
        for (M baseNode : nodes) {
            if (!allIds.contains(baseNode.getParentId()) || selfIdEqSelfParent.contains(baseNode.getParentId())) {
                trees.add(baseNode);
            }

            // 叶子节点设置
            baseNode.setLeaf(CollectionUtils.isEmpty(baseNode.getChildren()));
        }
        return trees;
    }

}
