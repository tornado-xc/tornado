package com.xingchi.tornado.sms.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.config.annotation.ConditionalOnNotePlatform;
import com.xingchi.tornado.sms.config.model.NoteProperties;
import com.xingchi.tornado.sms.exception.BuilderNotePlatformClientException;
import com.xingchi.tornado.sms.service.SmsService;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import com.xingchi.tornado.sms.service.impl.AliyunSmsServiceSupport;
import com.xingchi.tornado.sms.service.impl.DefaultNoteServiceSupport;
import com.xingchi.tornado.sms.service.impl.EmailServiceSupport;
import com.xingchi.tornado.sms.service.impl.TencentSmsServiceSupport;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.security.AccessControlException;

/**
 * 短信通道自动配置
 *
 * @author xingchi
 * @date 2022/12/1 22:06
 * @modified xingchi
 */
@Configuration
@MapperScan("com.xingchi.framework.note.dao")
@EnableConfigurationProperties({NoteProperties.class})
public class NoteAutoConfiguration {

    @Autowired
    private NoteProperties noteProperties;

    @Bean
    @ConditionalOnClass(Client.class)
    @ConditionalOnNotePlatform(value = PlatformType.ALIYUN)
    public Client client(NoteProperties noteProperties) {

        String accessId = noteProperties.getAliyun().getAccessId();
        String accessSecret = noteProperties.getAliyun().getAccessSecret();

        if (StringUtils.isBlank(accessId) || StringUtils.isBlank(accessSecret)) {
            throw new AccessControlException("Properties 'accessId' or 'accessSecret' can't is empty!");
        }

        try {
            Config config = new Config()
                    .setAccessKeyId(accessId)
                    .setAccessKeySecret(accessSecret)
                    .setEndpoint(noteProperties.getAliyun().getEndpoint());
            return new Client(config);
        } catch (Exception e) {
            throw new BuilderNotePlatformClientException(String.format("create aliyun operator Client fail. cause: %s", e.getMessage()));
        }

    }

    @Bean
    @ConditionalOnNotePlatform(value = PlatformType.ALIYUN)
    public SmsService aliyunSmsServiceSupport(Client client, NoteTemplateService noteTemplateService) {
        return new AliyunSmsServiceSupport(client, noteTemplateService);
    }

    @Bean
    @ConditionalOnClass({Credential.class, ClientProfile.class})
    @ConditionalOnNotePlatform(value = PlatformType.TENCENT)
    public SmsClient smsClient(NoteProperties noteProperties) {

        String accessId = noteProperties.getTencentCloud().getAccessId();
        String accessSecret = noteProperties.getTencentCloud().getAccessSecret();

        // 参数校验
        if (StringUtils.isBlank(accessId) || StringUtils.isBlank(accessSecret)) {
            throw new AccessControlException("Properties 'accessId' or 'accessSecret' can't is empty!");
        }

        Credential credential = new Credential(accessId, accessSecret);
        try {
            return new SmsClient(credential, noteProperties.getTencentCloud().getRegionId(), new ClientProfile());
        } catch (Exception e) {
            throw new BuilderNotePlatformClientException(String.format("create TencentCloud SMS operator Client fail. cause: %s", e.getMessage()));
        }
    }

    @Bean
    @ConditionalOnClass({Credential.class, ClientProfile.class})
    @ConditionalOnNotePlatform(value = PlatformType.TENCENT)
    public SmsService tencentSmsServiceSupport(SmsClient smsClient, NoteTemplateService noteTemplateService, NoteProperties noteProperties) {
        return new TencentSmsServiceSupport(smsClient, noteTemplateService, noteProperties);
    }

    @Bean
    @ConditionalOnNotePlatform(value = PlatformType.EMAIL)
    public SmsService emailNoteService(NoteTemplateService noteTemplateService) {
        return new EmailServiceSupport(noteTemplateService, noteProperties.getEmail());
    }

    @Bean
    @Order
    @ConditionalOnMissingBean
    public SmsService defaultNoteService(NoteTemplateService noteTemplateService) {
        return new DefaultNoteServiceSupport(noteTemplateService);
    }

}
