package com.xingchi.tornado.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询参数
 *
 * @author xingchi
 * @date 2023/6/10 21:14
 * @modified xingchi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationQuery {

    /**
     * 当前页码
     */
    @Builder.Default
    private int pageNum = 1;

    /**
     * 每页记录数
     */
    @Builder.Default
    private int pageSize = 20;

}
