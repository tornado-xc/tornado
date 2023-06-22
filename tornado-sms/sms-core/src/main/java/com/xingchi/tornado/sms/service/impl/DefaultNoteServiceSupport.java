package com.xingchi.tornado.sms.service.impl;

import com.xingchi.tornado.basic.BaseParameter;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.AbstractSmsService;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认通知实现，为空
 *
 * @author xingchi
 * @date 2023/1/19 23:32
 * @modified xingchi
 */
@Slf4j
public class DefaultNoteServiceSupport extends AbstractSmsService {

    public DefaultNoteServiceSupport(NoteTemplateService noteTemplateService) {
        super(noteTemplateService);
    }

    @Override
    public Integer platformType() {
        return PlatformType.DEFAULT.code();
    }

    @Override
    public boolean execute(String account, NoteTemplate template, BaseParameter parameter) {
        return true;
    }
}
