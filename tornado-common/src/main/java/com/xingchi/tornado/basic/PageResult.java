package com.xingchi.tornado.basic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页参数
 *
 * @author xingchi
 * @date 2023/6/10 18:30
 * @modified xingchi
 */
@Data
@Schema(name = "分页结果返回")
public class PageResult<T> {

    /**
     * 当前页码
     */
    @Schema(name = "当前页码")
    private long pageNum;

    /**
     * 获取每页显示条数
     */
    @Schema(name = "获取每页显示条数")
    private long pageSize;

    /**
     * 总页数
     */
    @Schema(name = "总页数")
    private long pageCount;

    /**
     * 总记录数
     */
    @Schema(name = "总记录数")
    private long totals;

    /**
     * 实际数据项
     */
    @Schema(name = "实际数据项")
    private List<T> items;

    /**
     * 分页转换将 mybatisplus 中的 {@link  IPage} 转换为 {@link  PageResult}
     *
     * @param page      IPage
     * @param handler   转换处理函数
     * @return          PageResult
     * @param <T>       原始数据类型
     * @param <R>       转换后的数据类型
     */
    public static <T, R> PageResult<R> fetchPage(IPage<T> page, Function<T, R> handler) {

        PageResult<R> pageResult = new PageResult<>();

        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());

        pageResult.setTotals(page.getTotal());
        pageResult.setPageCount(page.getPages());

        if (CollectionUtils.isEmpty(page.getRecords())) {
            page.setRecords(Collections.emptyList());
        }
        pageResult.setItems(page.getRecords().stream().map(handler).collect(Collectors.toList()));

        return pageResult;
    }

    /**
     * 页转换将 mybatisplus 中的 {@link  IPage} 转换为 {@link  PageResult} 不需要做额外的处理
     *
     * @param page      分页数据
     * @return          PageResult
     * @param <T>       数据的实际类型
     */
    public static <T> PageResult<T> fetchPage(IPage<T> page) {
        return fetchPage(page, Function.identity());
    }

}
