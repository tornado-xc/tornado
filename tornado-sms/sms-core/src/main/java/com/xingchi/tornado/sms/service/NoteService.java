package com.xingchi.tornado.sms.service;

import com.xingchi.tornado.sms.common.model.dto.NoticeDTO;

/**
 * @author xingchi
 * @date 2023/6/21 22:14
 * @modified xingchi
 */
public interface NoteService {

    /**
     * 发送短信
     *
     * @param notice        短信元数据
     * @return              是否发送成功
     */
    boolean sendNotice(NoticeDTO notice);
}
