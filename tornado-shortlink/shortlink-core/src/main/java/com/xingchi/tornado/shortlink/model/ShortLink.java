package com.xingchi.tornado.shortlink.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短链映射表
 */
@TableName(value ="xc_shortlink")
@Data
public class ShortLink implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 短链id
     */
    private String shortId;

    /**
     * 长链url
     */
    private String longUrl;

    /**
     * 服务名称
     */
    private String serviceName;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
