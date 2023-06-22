package com.xingchi.tornado.sms.event;

import com.xingchi.tornado.sms.model.NoteLogRecord;
import lombok.Getter;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.ApplicationEvent;

/**
 * 短信发送事件
 *
 * @author xingchi
 * @date 2023/6/22 18:48
 * @modified xingchi
 */
@Getter
public class SmsSendEvent extends ApplicationEvent {

    private final NoteLogRecord record;

    public SmsSendEvent(NoteLogRecord record) {
        super(record);
        this.record = record;
    }

}
