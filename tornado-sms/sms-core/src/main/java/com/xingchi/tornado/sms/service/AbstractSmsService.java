package com.xingchi.tornado.sms.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xingchi.tornado.core.context.IdContextHolder;
import com.xingchi.tornado.core.context.SpringContextHolder;
import com.xingchi.tornado.sms.common.enums.OperateStatus;
import com.xingchi.tornado.sms.event.SmsSendEvent;
import com.xingchi.tornado.sms.model.NoteLogRecord;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.basic.BaseParameter;
import com.xingchi.tornado.core.exception.ExceptionWrap;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 短信服务抽象接口，定义短信发送的基本步骤
 *
 * @author xingchi
 * @date 2023/1/19 21:46
 * @modified xingchi
 */
public abstract class AbstractSmsService implements SmsService {

    private final NoteTemplateService noteTemplateService;

    public AbstractSmsService(NoteTemplateService noteTemplateService) {
        this.noteTemplateService = noteTemplateService;
    }

    @Override
    public boolean send(String account, String template, BaseParameter parameter) {

        // 校验模板参数 可以为空集合但是不能为null
        if (Objects.isNull(parameter)) {
            ExceptionWrap.cast("The template parameter cannot be null");
        }

        // 获取模板信息
        NoteTemplate noteTemplate = noteTemplateService.getBaseMapper().selectOne(Wrappers.<NoteTemplate>lambdaQuery().eq(NoteTemplate::getBusinessType, template));

        if (noteTemplate == null) {
            ExceptionWrap.cast("The specified template could not be found");
        }

        // 校验模板与参数个数是否匹配
        boolean verifyResult = this.verifyTemplateParam(noteTemplate.getContent(), parameter);
        if (!verifyResult) {
            ExceptionWrap.cast(String.format("The parameter number does not match the template parameter number. template: %s, parameter: %s", noteTemplate.getContent(), parameter));
        }

        NoteLogRecord record = new NoteLogRecord();
        record.setAccount(account);
        record.setStatus(OperateStatus.SUCCESS.code());
        record.setCause("成功");
        record.setTemplateId(noteTemplate.getId());
        record.setMessageId(IdContextHolder.get());
        record.setContent(this.fill(noteTemplate.getContent(), parameter));
        try {
            // 执行实际的发送操作，交由子类实现
            boolean execute = this.execute(account, noteTemplate, parameter);
            SpringContextHolder.publishEvent(new SmsSendEvent(record));
            return execute;
        } catch (Exception e) {
            record.setStatus(OperateStatus.FAIL.code());
            record.setCause(e.getMessage());
            SpringContextHolder.publishEvent(new SmsSendEvent(record));
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean send(List<String> accounts, String template, BaseParameter parameter) {
        if (CollectionUtils.isEmpty(accounts)) {
            ExceptionWrap.cast("Send SMS fail. cause: accounts is 'null'");
        }
        String collect = String.join(",", accounts);
        return this.send(collect, template, parameter);
    }

    /**
     * z执行发送
     *
     * @param account           发送的账户，多个账户采用,分隔的形式
     * @param template          模板
     * @param parameter         参数
     * @return                  是否发送成功
     */
    public abstract boolean execute(String account, NoteTemplate template, BaseParameter parameter);

}
