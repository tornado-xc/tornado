package com.xingchi.tornado.file.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingchi.tornado.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件用户关联
 *
 * @author xingchi
 * @date 2024/1/13 12:19
 */
@Data
@TableName("file")
@EqualsAndHashCode(callSuper = true)
public class FileUserRelDO extends BaseEntity {

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件id用户id
     */
    private Long userId;

}
