package com.xingchi.sms.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId(type = IdType.NONE)
    @TableField(fill = FieldFill.INSERT)
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
     * 通知类型 {@link com.xingchi.sms.common.enums.NoteType#code()}
     */
    private Integer type;

    /**
     * 平台类型 {@link com.xingchi.sms.common.enums.PlatformType#code()}
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
    private Integer deleted;

    /**
     * 签名
     */
    private String signName;

}
