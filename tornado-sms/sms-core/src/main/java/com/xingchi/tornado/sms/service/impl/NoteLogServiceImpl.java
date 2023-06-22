package com.xingchi.tornado.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingchi.tornado.sms.dao.NoteLogRecordDao;
import com.xingchi.tornado.sms.model.NoteLogRecord;
import com.xingchi.tornado.sms.service.NoteLogService;
import org.springframework.stereotype.Service;

/**
 * 短信日志记录其实现类
 *
 * @author xingchi
 * @date 2023/6/22 19:00
 * @modified xingchi
 */
@Service
public class NoteLogServiceImpl extends ServiceImpl<NoteLogRecordDao, NoteLogRecord> implements NoteLogService {
}
