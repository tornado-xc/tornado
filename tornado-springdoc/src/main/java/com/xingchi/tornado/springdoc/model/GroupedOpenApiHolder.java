package com.xingchi.tornado.springdoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springdoc.core.GroupedOpenApi;

/**
 * 用于解决分组排序问题
 *
 * @author xingchi
 * @date 2023/10/2 17:22
 * @modified xingchi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedOpenApiHolder {

    private GroupedOpenApi groupedOpenApi;

    private int order;

}
