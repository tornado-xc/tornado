package com.xingchi.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchi.sms.common.model.dto.NoteTemplateQuery;
import com.xingchi.sms.model.NoteTemplate;
import com.xingchi.tornado.basic.PageResult;
import com.xingchi.tornado.basic.PaginationQuery;

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
    List<NoteTemplate> findAll();

    /**
     * 根据id查询单条记录
     *
     * @param id        id
     * @return          模板记录
     */
    NoteTemplate selectById(Long id);

    /**
     * 业务参数类型
     *
     * @param businessType      业务类型
     * @return                  模板
     */
    NoteTemplate selectByBusinessType(String businessType);

    /**
     * 更新模板信息
     *
     * @param noteTemplate      模板信息
     * @return                  更新后的数据
     */
    NoteTemplate updateTemplateById(NoteTemplate noteTemplate);

    /**
     * 添加一个模板
     *
     * @param noteTemplate      模板信息
     * @return                  添加后的模板
     */
    NoteTemplate create(NoteTemplate noteTemplate);

    /**
     * 分页查询所有模板信息
     *
     * @param query             分页参数
     * @return
     */
    PageResult<NoteTemplate> pageList(NoteTemplateQuery query);
}
