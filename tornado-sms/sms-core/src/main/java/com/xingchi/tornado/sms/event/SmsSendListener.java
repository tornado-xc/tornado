package com.xingchi.tornado.sms.event;

import com.xingchi.tornado.constant.Separator;
import com.xingchi.tornado.sms.model.NoteLogRecord;
import com.xingchi.tornado.sms.service.NoteLogService;
import com.xingchi.tornado.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 监听器
 *
 * @author xingchi
 * @date 2023/6/22 18:56
 * @modified xingchi
 */
@Component
@RequiredArgsConstructor
public class SmsSendListener {

    private final NoteLogService noteLogService;

    @Async
    @EventListener(SmsSendEvent.class)
    public void handlerSendEvent(SmsSendEvent event) {
        NoteLogRecord record = event.getRecord();

        String account = record.getAccount();
        String[] accounts = account.split(Separator.COMMA);

        if (accounts.length == 1) {
            noteLogService.save(record);
            return;
        }

        List<NoteLogRecord> records = new ArrayList<>();
        for (String signAccount : accounts) {

            NoteLogRecord signRecord = BeanCopyUtils.copyProperties(record, NoteLogRecord.class);
            signRecord.setAccount(signAccount);
            records.add(signRecord);
        }
        noteLogService.saveBatch(records);
    }

}
