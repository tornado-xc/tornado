package com.xingchi.tornado.sms.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.xingchi.tornado.basic.BaseParameter;
import com.xingchi.tornado.constant.Separator;
import com.xingchi.tornado.core.exception.ExceptionWrap;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.config.model.NoteProperties;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.AbstractSmsService;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaoya
 * @date 2022/12/9 13:41
 * @modified xiaoya
 */
@Slf4j
public class TencentSmsServiceSupport extends AbstractSmsService {

    private final SmsClient smsClient;

    private final NoteProperties noteProperties;

    public TencentSmsServiceSupport(SmsClient smsClient, NoteTemplateService noteTemplateService, NoteProperties noteProperties) {
        super(noteTemplateService);
        this.smsClient = smsClient;
        this.noteProperties = noteProperties;
    }

    @Override
    public boolean execute(String account, NoteTemplate template, BaseParameter parameter) {

        // 设置请求的基本信息，签名，模板id，应用名称
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSignName(template.getSignName());
        sendSmsRequest.setTemplateId(template.getCode());
        sendSmsRequest.setSmsSdkAppId(template.getAppId());

        // 设置发送的账户
        String[] accounts = account.split(Separator.COMMA);
        sendSmsRequest.setPhoneNumberSet(accounts);

        // 设置模板参数
        String[] params = (String[]) parameter.values().stream().map(Object::toString).toArray();
        sendSmsRequest.setTemplateParamSet(params);

        // 执行发送操作
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = smsClient.SendSms(sendSmsRequest);
            log.info("响应参数: {}", JSONObject.toJSONString(sendSmsResponse));
        } catch (TencentCloudSDKException e) {
            log.error("Send SMS fail. cause: {}", e.getMessage());
            ExceptionWrap.cast(e.getMessage());
        }
        return true;
    }

    @Override
    public Integer platformType() {
        return PlatformType.TENCENT.code();
    }

}
