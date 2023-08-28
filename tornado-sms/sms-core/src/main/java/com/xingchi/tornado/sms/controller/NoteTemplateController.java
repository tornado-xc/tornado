package com.xingchi.tornado.sms.controller;

import com.xingchi.tornado.core.validation.Create;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateDTO;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateQuery;
import com.xingchi.tornado.sms.common.model.vo.NoteTemplateVO;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import com.xingchi.tornado.core.model.PageResult;
import com.xingchi.tornado.basic.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 短信模板控制器
 *
 * @author xingchi
 * @date 2023/6/5 20:34
 * @modified xingchi
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "短信模板控制器", description = "包含短信模板相关的查询、修改、添加等相关接口")
@RequestMapping("/template")
public class NoteTemplateController {

    private final NoteTemplateService noteTemplateService;

    @GetMapping("/list/all")
    @Operation(summary = "查询所有的短信模板信息", description = "查询所有的短信模板信息不分页")
    public Result<List<NoteTemplateVO>> findAll() {
        // 查询所有短信模板
        return Result.ok(noteTemplateService.findAll());
    }

    @GetMapping("/list/page")
    @Operation(summary = "分页查询短信模板信息")
    public Result<PageResult<NoteTemplateVO>> pageList(NoteTemplateQuery query) {
        return Result.ok(noteTemplateService.pageList(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询出指定模板信息")
    public Result<NoteTemplateVO> findById(@PathVariable("id") Long id) {
        return Result.ok(noteTemplateService.selectById(id));
    }

    @GetMapping
    @Operation(summary = "根据业务类型查询指定的短信模板", description = "根据业务消息类型查询指定模板，如：NOTE_CODE、SYSTEM_NOTICE等")
    public Result<NoteTemplateVO> findByBusinessType(@RequestParam("businessType") String businessType) {
        return Result.ok(noteTemplateService.selectByBusinessType(businessType));
    }

    @PostMapping
    @Operation(summary = "新增一个短信模板", description = "创建一个新的短信模板")
    public Result<Boolean> createNoteTemplate(@RequestBody @Validated(value = Create.class) NoteTemplateDTO noteTemplateDTO) {
        return Result.ok(noteTemplateService.create(noteTemplateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新短信模板", description = "更新模板信息")
    public Result<Boolean> updateTemplate(@PathVariable("id") Long id,
                                          @RequestBody NoteTemplateDTO noteTemplateDTO) {
        return Result.ok(noteTemplateService.updateTemplate(id, noteTemplateDTO));
    }

}
