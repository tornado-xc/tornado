package com.xingchi.tornado.sms.common.model.vo;

import com.xingchi.tornado.core.config.anno.JsonEnum;
import com.xingchi.tornado.core.plugins.anno.AutoId;
import com.xingchi.tornado.sms.common.enums.OperateStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 日志记录VO
 *
 * @author xingchi
 * @date 2023/6/22 19:22
 * @modified xingchi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "短信发送日志记录VO")
public class NoteLogRecordVO {

    @AutoId
    private Long id;

    /**
     * 使用的模板id
     */
    @Schema(name = "使用的模板id")
    private Long templateId;

    /**
     * 消息id
     */
    @Schema(name = "消息id")
    @AutoId
    private Long messageId;

    /**
     * 目标账号
     */
    @Schema(name = "目标账号")
    private String account;

    /**
     * 内容
     */
    @Schema(name = "内容")
    private String content;

    /**
     * 发送状态 1 成功、0 失败
     */
    @JsonEnum(OperateStatus.class)
    @Schema(name = "发送状态")
    private Integer status;

    /**
     * 失败原因
     */
    @Schema(name = "失败原因")
    private String cause;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(name = "更新时间")
    private LocalDateTime updateTime;

}
