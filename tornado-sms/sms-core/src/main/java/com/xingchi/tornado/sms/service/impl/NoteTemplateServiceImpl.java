package com.xingchi.tornado.sms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingchi.tornado.sms.common.enums.NoteType;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateDTO;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateQuery;
import com.xingchi.tornado.sms.common.model.vo.NoteTemplateVO;
import com.xingchi.tornado.sms.dao.NoteTemplateDao;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import com.xingchi.tornado.basic.PageResult;
import com.xingchi.tornado.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
    public List<NoteTemplateVO> findAll() {
        List<NoteTemplate> noteTemplates = noteTemplateDao.selectList(Wrappers.<NoteTemplate>lambdaQuery());
        if (CollectionUtils.isEmpty(noteTemplates)) {
            return Collections.emptyList();
        }
        return BeanCopyUtils.copyList(noteTemplates, NoteTemplateVO.class);
    }

    @Override
    public NoteTemplateVO selectById(Long id) {
        NoteTemplate noteTemplate = noteTemplateDao.selectById(id);
        Assert.notNull(noteTemplate, "短信模板不存在");
        return BeanCopyUtils.copyProperties(noteTemplate, NoteTemplateVO.class);
    }

    @Override
    public NoteTemplateVO selectByBusinessType(String businessType) {

        NoteTemplate noteTemplate = noteTemplateDao.selectOne(Wrappers.<NoteTemplate>lambdaQuery().eq(NoteTemplate::getBusinessType, businessType));
        Assert.notNull(noteTemplate, String.format("指定业务类型的消息模板不存在'%s'", businessType));
        return BeanCopyUtils.copyProperties(noteTemplate, NoteTemplateVO.class);
    }

    @Override
    public PageResult<NoteTemplateVO> pageList(NoteTemplateQuery query) {
        Page<NoteTemplate> noteTemplatePage = this.page(new Page<>(query.getPageNum(), query.getPageSize()), null);
        return PageResult.fetchPage(noteTemplatePage, f -> BeanCopyUtils.copyProperties(f, NoteTemplateVO.class));
    }

    @Override
    public Boolean create(NoteTemplateDTO noteTemplateDTO) {

        Integer platform = noteTemplateDTO.getPlatform();
        if (!PlatformType.exists(platform)) {
            platform = PlatformType.OTHER.code();
        }

        Integer type = noteTemplateDTO.getType();
        if (!NoteType.exists(type)) {
            type = NoteType.OTHER.code();
        }

        Assert.notNull(noteTemplateDTO.getCode(), "请指定消息模板编码");

        Assert.isTrue(StringUtils.isNotBlank(noteTemplateDTO.getSignName()), "模板签名不能为空");

        noteTemplateDTO.setPlatform(platform);
        noteTemplateDTO.setType(type);
        return this.save(BeanCopyUtils.copyProperties(noteTemplateDTO, NoteTemplate.class));
    }
}
