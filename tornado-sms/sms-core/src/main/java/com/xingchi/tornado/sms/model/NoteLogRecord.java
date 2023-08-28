package com.xingchi.tornado.sms.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xingchi.tornado.core.plugins.anno.AutoId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短信日志记录实体
 *
 * @author xingchi
 * @date 2023/6/21 22:26
 * @modified xingchi
 */
@Data
public class NoteLogRecord {

    @AutoId
    private Long id;

    /**
     * 使用的模板id
     */
    private Long templateId;

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 目标账号
     */
    private String account;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送状态 1 成功、0 失败
     */
    private Integer status;

    /**
     * 失败原因
     */
    private String cause;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    private Integer deleted;

}
