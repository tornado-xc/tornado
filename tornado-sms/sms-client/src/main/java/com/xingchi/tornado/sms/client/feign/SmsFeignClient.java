package com.xingchi.tornado.sms.client.feign;

import com.xingchi.tornado.basic.Result;
import com.xingchi.tornado.sms.common.model.dto.NoticeDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xingchi
 * @date 2023/11/3 20:29
 */
@FeignClient(value = "tornado-sms-service", path = "/sms/note")
public interface SmsFeignClient {

    @PostMapping("/send")
    @Operation(summary = "发送短信相关接口", description = "查询所有的短信模板信息不分页")
    Result<Boolean> sendNote(@RequestBody @Validated NoticeDTO notice);

}
