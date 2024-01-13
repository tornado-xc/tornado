package com.xingchi.tornado.file.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingchi.tornado.core.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件基础表
 *
 * @author xingchi
 * @date 2024/1/12 22:15
 */
@Data
@TableName("file")
@EqualsAndHashCode(callSuper = true)
public class FileDO extends BaseDO {

    /**
     * 文件名称
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件hash
     */
    private String md5;

}
