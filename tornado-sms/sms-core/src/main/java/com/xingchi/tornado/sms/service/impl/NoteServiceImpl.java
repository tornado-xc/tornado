package com.xingchi.tornado.sms.service.impl;

import com.xingchi.tornado.sms.common.model.dto.NoticeDTO;
import com.xingchi.tornado.sms.service.NoteService;
import com.xingchi.tornado.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xingchi
 * @date 2023/6/21 22:15
 * @modified xingchi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final SmsService smsService;

    @Override
    @Transactional
    public boolean sendNotice(NoticeDTO notice) {
        // 执行发送
        return smsService.send(notice.getAccount(), notice.getTemplateCode(), notice.getParameter());
    }

}
