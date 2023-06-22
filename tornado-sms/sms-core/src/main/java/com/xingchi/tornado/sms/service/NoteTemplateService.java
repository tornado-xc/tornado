package com.xingchi.tornado.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateDTO;
import com.xingchi.tornado.sms.common.model.dto.NoteTemplateQuery;
import com.xingchi.tornado.sms.common.model.vo.NoteTemplateVO;
import com.xingchi.tornado.sms.model.NoteTemplate;
import com.xingchi.tornado.basic.PageResult;

import java.util.List;

/**
 * 模板服务
 *
 * @author xingchi
 * @date 2022/12/4 13:23
 * @modified xingchi
 */
public interface NoteTemplateService extends IService<NoteTemplate> {

    /**
     * 查询所有模板信息
     *
     * @return          所有模板信息
     */
    List<NoteTemplateVO> findAll();

    /**
     * 根据id查询单条记录
     *
     * @param id        id
     * @return          模板记录
     */
    NoteTemplateVO selectById(Long id);

    /**
     * 业务参数类型
     *
     * @param businessType      业务类型
     * @return                  模板
     */
    NoteTemplateVO selectByBusinessType(String businessType);

    /**
     * 添加一个模板
     *
     * @param noteTemplate      模板信息
     * @return                  添加后的模板
     */
    Boolean create(NoteTemplateDTO noteTemplate);

    /**
     * 分页查询所有模板信息
     *
     * @param query             分页参数
     * @return
     */
    PageResult<NoteTemplateVO> pageList(NoteTemplateQuery query);

    /**
     * 根据id更新短信模板信息
     *
     * @param id                短信模板id
     * @param noteTemplateDTO   需要更新的信息
     * @return                  是否更新成功
     */
    Boolean updateTemplate(Long id, NoteTemplateDTO noteTemplateDTO);
}
