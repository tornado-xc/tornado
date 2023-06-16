package com.xingchi.tornado.sms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingchi.tornado.sms.common.enums.NoteType;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateQuery;
import com.xingchi.tornado.sms.dao.NoteTemplateDao;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import com.xingchi.tornado.basic.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author xingchi
 * @date 2022/12/4 13:24
 * @modified xingchi
 */
@Service
public class NoteTemplateServiceImpl extends ServiceImpl<NoteTemplateDao, NoteTemplate> implements NoteTemplateService {

    @Autowired
    private NoteTemplateDao noteTemplateDao;

    @Override
    public List<NoteTemplate> findAll() {
        return noteTemplateDao.selectList(Wrappers.<NoteTemplate>lambdaQuery());
    }

    @Override
    public NoteTemplate selectById(Long id) {
        NoteTemplate noteTemplate = noteTemplateDao.selectById(id);
        Assert.notNull(noteTemplate, "短信模板不存在");
        return noteTemplate;
    }

    @Override
    public NoteTemplate selectByBusinessType(String businessType) {

        NoteTemplate noteTemplate = noteTemplateDao.selectOne(Wrappers.<NoteTemplate>lambdaQuery().eq(NoteTemplate::getBusinessType, businessType));
        Assert.notNull(noteTemplate, String.format("指定业务类型的消息模板不存在'%s'", businessType));
        return noteTemplate;
    }

    @Override
    public PageResult<NoteTemplate> pageList(NoteTemplateQuery query) {
        Page<NoteTemplate> noteTemplatePage = this.page(new Page<>(query.getPageNum(), query.getPageSize()), null);
        return PageResult.fetchPage(noteTemplatePage);
    }

    @Override
    public NoteTemplate create(NoteTemplate noteTemplate) {

        noteTemplate.setId(null);
        Integer platform = noteTemplate.getPlatform();
        if (!PlatformType.exists(platform)) {
            platform = PlatformType.OTHER.code();
        }

        String content = noteTemplate.getContent();
        Assert.isTrue(StringUtils.isNotBlank(content), "消息模板不能为空");

        Integer type = noteTemplate.getType();
        if (!NoteType.exists(type)) {
            type = NoteType.OTHER.code();
        }

        Assert.notNull(noteTemplate.getCode(), "请指定消息模板编码");

        Assert.isTrue(StringUtils.isNotBlank(noteTemplate.getSignName()), "模板签名不能为空");

        noteTemplate.setPlatform(platform);
        noteTemplate.setType(type);
        this.save(noteTemplate);
        return noteTemplate;
    }
}
