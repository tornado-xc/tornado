package com.xingchi.tornado.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础DO，包含数据库实体中的公共字段
 *
 * @author xingchi
 * @date 2024/1/7 19:40
 */
@Data
public abstract class BaseDO implements Serializable {

    private static final long serialVersionUID = 6876197702303904380L;

    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
