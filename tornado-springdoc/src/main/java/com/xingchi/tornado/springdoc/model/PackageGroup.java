package com.xingchi.tornado.springdoc.model;

import lombok.Data;

/**
 * 根据包分组model
 *
 * @author xiaoya
 * @date 2022/12/6 13:26
 * @modified xiaoya
 */
@Data
public class PackageGroup {

    private String groupName;

    private String packageName;

    private int order = 0;

}
