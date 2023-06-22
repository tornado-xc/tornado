package com.xingchi.tornado.sms.common.model.dto;

import com.xingchi.tornado.basic.BaseParameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author xingchi
 * @date 2023/6/21 22:00
 * @modified xingchi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "短信通知信息")
public class NoticeDTO {

    /**
     * 模板编码
     */
    @Schema(name = "模板编码")
    @NotNull(message = "模板编码不能为空")
    private String templateCode;

    /**
     * 账户
     */
    @Schema(name = "账户")
    @NotNull(message = "发送账户不能为空")
    private String account;

    /**
     * 参数信息
     */
    @Schema(name = "参数信息")
    @NotEmpty(message = "参数不能为空")
    private BaseParameter parameter;

}
