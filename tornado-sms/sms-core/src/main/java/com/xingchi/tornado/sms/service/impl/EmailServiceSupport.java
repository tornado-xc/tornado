package com.xingchi.tornado.sms.service.impl;

import com.xingchi.tornado.basic.BaseParameter;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.config.model.NoteProperties;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.AbstractSmsService;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;

/**
 * @author xingchi
 * @date 2023/10/4 9:00
 * @modified xingchi
 */
@Slf4j
public class EmailServiceSupport extends AbstractSmsService {

    private final NoteProperties.EmailProperties emailProperties;
    private Session session;

    public EmailServiceSupport(NoteTemplateService noteTemplateService, NoteProperties.EmailProperties emailProperties) {
        super(noteTemplateService);

        this.emailProperties = emailProperties;
        try {
            // 定义Properties对象，设置环境信息
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", emailProperties.getHost());
            props.setProperty("mail.mime.splitlongparameters", "false");
            props.setProperty("mail.mime.charset", "UTF-8");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.socketFactory.port", emailProperties.getPort().toString());

            boolean ssl = BooleanUtils.isTrue(emailProperties.getSsl());
            props.put("mail.smtp.ssl.enable", ssl);
            if (ssl) {
                props.setProperty("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());
            }
            this.session = this.getSession(props);
            this.session.setDebug(BooleanUtils.isTrue(emailProperties.getDebug()));
        } catch (Exception e) {
            log.error("\n=============发送邮件功能初始化异常==========\ne:{}\n=============发送邮件功能初始化异常==========", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String account, NoteTemplate template, BaseParameter parameter) {

        // 查询模板
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(emailProperties.getUsername());
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(account));
            mimeMessage.setSubject(template.getSignName());
            mimeMessage.setText(this.fill(template.getContent(), template.getSignName(), parameter));
            Transport.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public Session getSession(Properties properties) {
        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
            }
        });
    }

    @Override
    public Integer platformType() {
        return PlatformType.EMAIL.code();
    }

}
