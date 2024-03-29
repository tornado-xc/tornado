package com.xingchi.tornado.sms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xingchi.tornado.sms.model.NoteTemplate;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模板操作相关接口
 *
 * @author xingchi
 * @date 2022/12/4 13:26
 * @modified xingchi
 */
@Mapper
public interface NoteTemplateDao extends BaseMapper<NoteTemplate> {

}
