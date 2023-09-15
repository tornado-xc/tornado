package com.xingchi.tornado.sms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingchi.tornado.core.model.PageResult;
import com.xingchi.tornado.sms.common.enums.NoteType;
import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateDTO;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateQuery;
import com.xingchi.tornado.sms.common.model.vo.NoteTemplateVO;
import com.xingchi.tornado.sms.dao.NoteTemplateDao;
import com.xingchi.tornado.sms.exception.BuilderNotePlatformClientException;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.sms.service.NoteTemplateService;
import com.xingchi.tornado.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@RequiredArgsConstructor
public class NoteTemplateServiceImpl extends ServiceImpl<NoteTemplateDao, NoteTemplate> implements NoteTemplateService {

    private final NoteTemplateDao noteTemplateDao;

    @Override
    public List<NoteTemplateVO> findAll() {
        List<NoteTemplate> noteTemplates = noteTemplateDao.selectList(Wrappers.<NoteTemplate>lambdaQuery());
        if (CollectionUtils.isEmpty(noteTemplates)) {
            return Collections.emptyList();
        }
        return BeanUtils.copyList(noteTemplates, NoteTemplateVO.class);
    }

    @Override
    public NoteTemplateVO selectById(Long id) {
        NoteTemplate noteTemplate = noteTemplateDao.selectById(id);
        Assert.notNull(noteTemplate, "短信模板不存在");
        return BeanUtils.copyProperties(noteTemplate, NoteTemplateVO.class);
    }

    @Override
    public NoteTemplateVO selectByBusinessType(String businessType) {

        NoteTemplate noteTemplate = noteTemplateDao.selectOne(Wrappers.<NoteTemplate>lambdaQuery().eq(NoteTemplate::getBusinessType, businessType));
        Assert.notNull(noteTemplate, String.format("指定业务类型的消息模板不存在'%s'", businessType));
        return BeanUtils.copyProperties(noteTemplate, NoteTemplateVO.class);
    }

    @Override
    public PageResult<NoteTemplateVO> pageList(NoteTemplateQuery query) {
        Page<NoteTemplate> noteTemplatePage = this.page(new Page<>(query.getPageNum(), query.getPageSize()), null);
        return PageResult.fetchPage(noteTemplatePage, f -> BeanUtils.copyProperties(f, NoteTemplateVO.class));
    }

    @Override
    public Boolean create(NoteTemplateDTO noteTemplateDTO) {

        Integer platform = noteTemplateDTO.getPlatform();

        PlatformType instance = PlatformType.getInstance(platform);
        if (instance == null) {
            platform = PlatformType.OTHER.code();
        }

        if (PlatformType.TENCENT.isType(instance.code())) {
            if (StringUtils.isBlank(noteTemplateDTO.getAppId()) || StringUtils.isBlank(noteTemplateDTO.getAppName())) {
                throw new BuilderNotePlatformClientException("Please specify appId or appName. appId and appName can't empty in TencentCloud platform");
            }
        }

        Integer type = noteTemplateDTO.getType();
        if (!NoteType.exists(type)) {
            type = NoteType.OTHER.code();
        }

        noteTemplateDTO.setPlatform(platform);
        noteTemplateDTO.setType(type);
        return this.save(BeanUtils.copyProperties(noteTemplateDTO, NoteTemplate.class));
    }

    @Override
    public Boolean updateTemplate(Long id, NoteTemplateDTO noteTemplateDTO) {

        NoteTemplate noteTemplate = this.baseMapper.selectById(id);
        Assert.notNull(noteTemplate, String.format("短信模板%s不存在", id));

        NoteTemplate template = BeanUtils.copyProperties(noteTemplateDTO, NoteTemplate.class);
        template.setId(id);
        return this.updateById(template);
    }
}
