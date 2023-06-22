package com.xingchi.tornado.sms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xingchi.tornado.sms.model.NoteLogRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xingchi
 * @date 2023/6/22 19:02
 * @modified xingchi
 */
@Mapper
public interface NoteLogRecordDao extends BaseMapper<NoteLogRecord> {
}
