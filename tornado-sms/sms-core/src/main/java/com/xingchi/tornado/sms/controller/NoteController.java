package com.xingchi.tornado.sms.controller;

import com.xingchi.tornado.basic.Result;
import com.xingchi.tornado.sms.common.model.dto.NoticeDTO;
import com.xingchi.tornado.sms.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 短信控制器
 *
 * @author xingchi
 * @date 2023/6/21 21:55
 * @modified xingchi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
@Tag(name = "短信发送控制器", description = "发送短信相关接口")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/send")
    @Operation(summary = "发送短信相关接口", description = "查询所有的短信模板信息不分页")
    public Result<Boolean> sendNote(@RequestBody @Validated NoticeDTO notice) {
        // 查询所有短信模板
        return Result.ok(noteService.sendNotice(notice));
    }

}
