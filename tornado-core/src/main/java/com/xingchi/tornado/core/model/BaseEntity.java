package com.xingchi.tornado.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xingchi.tornado.core.plugins.anno.AutoId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @param <T>       id类型
 * @author xingchi
 * @date 2022/8/18 22:32
 * @modified xingchi
 */
@Data
public abstract class BaseEntity<T> implements Serializable {

    @AutoId
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(fill = FieldFill.INSERT)
    private T id;

    @TableField(fill = FieldFill.INSERT)
    private T createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private T updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;

}
