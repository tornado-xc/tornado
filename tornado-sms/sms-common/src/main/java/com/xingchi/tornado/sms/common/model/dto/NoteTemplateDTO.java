package com.xingchi.tornado.sms.common.model.dto;

import com.xingchi.tornado.core.config.anno.JsonEnum;
import com.xingchi.tornado.core.validation.Create;
import com.xingchi.tornado.core.validation.EnumCheck;
import com.xingchi.tornado.sms.common.enums.NoteType;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author xingchi
 * @date 2023/6/5 21:51
 * @modified xingchi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "短信模板实体")
public class NoteTemplateDTO {

    /**
     * 模板编码（需在响应的平台上进行配置）
     */
    @Schema(name = "模板编码")
    @NotBlank(message = "消息模板编码不能为空", groups = Create.class)
    private String code;

    /**
     * 模板消息体
     */
    @Schema(name = "模板消息体")
    @NotBlank(message = "模板消息体不能为空", groups = Create.class)
    private String content;

    /**
     * 业务类型（业务中自行定义），如
     *      - 注册验证码：REGISTER_CODE
     *      - 生日问候：BIRTHDAY_PUSH
     *      - 支付推送：PAY_PUSH
     */
    @Schema(name = "业务类型-需唯一")
    @NotBlank(message = "业务类型不能为空", groups = Create.class)
    private String businessType;

    /**
     * 通知类型 {@link NoteType#code()}
     */
    @Schema(name = "通知类型")
    @JsonEnum(value = NoteType.class)
    @EnumCheck(value = NoteType.class, message = "不存在指定通知类型")
    private Integer type;

    /**
     * 平台类型 {@link PlatformType#code()}
     */
    @Schema(name = "平台类型")
    @EnumCheck(value = PlatformType.class, message = "不存在指定平台类型")
    @JsonEnum(value = PlatformType.class)
    private Integer platform;

    /**
     * 签名
     */
    @Schema(name = "签名")
    @NotBlank(message = "模板签名不能为空", groups = Create.class)
    private String signName;

    /**
     * 描述信息
     */
    @Schema(name = "描述信息")
    private String description;

}
