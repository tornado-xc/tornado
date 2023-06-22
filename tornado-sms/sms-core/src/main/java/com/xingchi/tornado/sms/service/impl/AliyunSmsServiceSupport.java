package com.xingchi.tornado.sms.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.xingchi.tornado.basic.BaseParameter;
import com.xingchi.tornado.core.exception.ExceptionWrap;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.AbstractSmsService;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信通道阿里云实现
 *
 * @author xingchi
 * @date 2022/12/6 21:07
 * @modified xingchi
 */
@Slf4j
public class AliyunSmsServiceSupport extends AbstractSmsService {

    private final Client client;

    public AliyunSmsServiceSupport(Client client, NoteTemplateService noteTemplateService) {
        super(noteTemplateService);
        this.client = client;
    }

    @Override
    public boolean execute(String account, NoteTemplate template, BaseParameter parameter) {
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(account)
                .setSignName(template.getSignName())
                .setTemplateCode(template.getCode())
                .setTemplateParam(JSONObject.toJSONString(parameter));

        RuntimeOptions runtime = new RuntimeOptions();
        try {
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(request, runtime);
            log.info("响应参数: {}", JSONObject.toJSONString(sendSmsResponse));
        } catch (TeaException error) {
            log.error("Send SMS fail. cause: {}", error.message);
            ExceptionWrap.cast(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            log.error("Send SMS fail. cause: {}", error.message);
            ExceptionWrap.cast(error.message);
        }
        return true;
    }

    @Override
    public Integer platformType() {
        return PlatformType.ALIYUN.code();
    }

}
