package com.xingchi.tornado;

import com.xingchi.tornado.tree.TreeNode;
import com.xingchi.tornado.utils.TreeUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构测试
 *
 * @author xiaoya
 * @date 2023/5/17 13:44
 * @modified xiaoya
 */
public class TreeUtilsTests {

    public static void main(String[] args) {

        List<Org> orgs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Org org = new Org();
            org.setId((long) i + 1);
            org.setParentId((long)i);
            org.setName("第" + (i + 1) + "级");
            orgs.add(org);
        }
        System.out.println("");

        for (int i = 0; i < 10; i++) {
            Org org = new Org();
            org.setId((long) i + 1 + 100);
            org.setParentId((long)i  + 100);
            org.setName("第" + (i + 1) + "级");
            orgs.add(org);
        }

        List<Org> build = TreeUtils.build(orgs);

        System.out.println();

    }

}

@Data
class Org implements TreeNode<Long, Org> {

    private Long id;

    private Long parentId;

    private String name;

    private List<Org> children;

    private boolean leaf;

}
