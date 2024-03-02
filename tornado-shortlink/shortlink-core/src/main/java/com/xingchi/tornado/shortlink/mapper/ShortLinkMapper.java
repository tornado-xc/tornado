package com.xingchi.tornado.shortlink.mapper;

import com.xingchi.tornado.shortlink.model.ShortLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author xingchi
* @description 针对表【xc_shortlink(短链映射表)】的数据库操作Mapper
* @createDate 2023-11-19 21:56:44
* @Entity com.xingchi.tornado.shortlink.model.ShortLink
*/
public interface ShortLinkMapper extends BaseMapper<ShortLink> {

    /**
     * 根据短链id查询长链信息
     *
     * @param shortId   短链id
     * @return          长链
     */
    String getLongUrlByShortId(@Param("shortId") String shortId);

}




