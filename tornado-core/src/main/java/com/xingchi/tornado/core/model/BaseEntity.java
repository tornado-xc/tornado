package com.xingchi.tornado.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xingchi.tornado.core.plugins.anno.AutoId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @param <T> id类型
 * @author xingchi
 * @date 2022/8/18 22:32
 * @modified xingchi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntity extends BaseDO {

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;

}
