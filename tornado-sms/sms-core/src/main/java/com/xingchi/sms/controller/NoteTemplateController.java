package com.xingchi.sms.controller;

import com.xingchi.sms.common.model.dto.NoteTemplateDTO;
import com.xingchi.sms.model.NoteTemplate;
import com.xingchi.sms.service.NoteTemplateService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/template")
public class NoteTemplateController {

    private NoteTemplateService noteTemplateService;

    @GetMapping("/list")
    public List<NoteTemplate> findAll() {
        // 查询所有短信模板
        return noteTemplateService.findAll();
    }

    @GetMapping("/{id}")
    public NoteTemplate findById(@PathVariable("id") Long id) {
        return noteTemplateService.selectById(id);
    }

    @GetMapping
    public NoteTemplate findByBusinessType(@RequestParam("businessType") String businessType) {
        return noteTemplateService.selectByBusinessType(businessType);
    }

    @PostMapping
    public NoteTemplate createNoteTemplate(@RequestBody NoteTemplateDTO noteTemplateDTO) {
        return null;
    }
}
