package com.xingchi.tornado.sms.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xingchi.tornado.core.plugins.anno.AutoId;
import com.xingchi.tornado.sms.common.enums.NoteType;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 模板对象
 *
 * @author xingchi
 * @date 2022/12/4 13:28
 * @modified xingchi
 */
@Data
@TableName("note_template")
public class NoteTemplate {

    /**
     * id
     */
    @AutoId
    private Long id;

    /**
     * 模板编码（需在响应的平台上进行配置）
     */
    private String code;

    /**
     * 模板消息体
     */
    private String content;

    /**
     * 业务类型（业务中自行定义），如
     *      - 注册验证码：REGISTER_CODE
     *      - 生日问候：BIRTHDAY_PUSH
     *      - 支付推送：PAY_PUSH
     */
    private String businessType;

    /**
     * 通知类型 {@link NoteType#code()}
     */
    private Integer type;

    /**
     * 平台类型 {@link PlatformType#code()}
     */
    private Integer platform;

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

    /**
     * 删除标记
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    /**
     * 签名
     */
    private String signName;

    /**
     * 描述信息 (查询时忽略该字段，该字段过于长，印象查询效率)
     */
    @TableField(select = false)
    private String description;

}
