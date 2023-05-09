package com.xingchi.unique.controller;

import com.xingchi.common.unique.IDProviderType;
import com.xingchi.tornado.basic.Result;
import com.xingchi.unique.service.IDGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 唯一码服务
 *
 * @author xingchi
 * @date 2023/3/10 20:19
 * @modified xingchi
 */
@RequestMapping("/unique")
@RestController
public class UniqueCodeController {

    private final IDGeneratorService idGeneratorService;

    public UniqueCodeController(IDGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @GetMapping("/{type}")
    public Result<String> uniqueId(@PathVariable("type") String type) {
        return Result.ok(idGeneratorService.uniqueId(IDProviderType.getInstance(type)));
    }

    @GetMapping("/snowflake")
    public Result<Long> snowflakeId() {
        return Result.ok(idGeneratorService.snowflakeId());
    }

    @GetMapping("/snowflake/{count}")
    public Result<List<Long>> snowflakeIds(@PathVariable("count") Integer count) {
        return Result.ok(idGeneratorService.snowflakeIds(count));
    }

    @GetMapping("/redis")
    public Result<Long> redisId() {
        return Result.ok(idGeneratorService.redisId());
    }

    @GetMapping("/redis/{count}")
    public Result<List<Long>> redisIds(@PathVariable("count") Integer count) {
        return Result.ok(idGeneratorService.redisIds(count));
    }

    @GetMapping("/{type}/{count}")
    public Result<List<String>> uniqueIds(@PathVariable("type") String type, @PathVariable("count") Integer count) {
        return Result.ok(idGeneratorService.uniqueIds(IDProviderType.getInstance(type), count));
    }

}
