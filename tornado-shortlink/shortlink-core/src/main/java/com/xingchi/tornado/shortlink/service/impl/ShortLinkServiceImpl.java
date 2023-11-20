package com.xingchi.tornado.shortlink.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingchi.tornado.constant.RegexConstants;
import com.xingchi.tornado.core.context.IdContextHolder;
import com.xingchi.tornado.core.exception.ExceptionWrap;
import com.xingchi.tornado.shortlink.model.ShortLink;
import com.xingchi.tornado.shortlink.model.dto.ShortLinkCreateDTO;
import com.xingchi.tornado.shortlink.service.ShortLinkService;
import com.xingchi.tornado.shortlink.mapper.ShortLinkMapper;
import com.xingchi.tornado.shortlink.service.ShortURLGenerateService;
import com.xingchi.tornado.shortlink.service.ShortURLRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.InstanceFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.xingchi.tornado.shortlink.utils.Constants.DEFAULT_SERVICE_NAME;

/**
* @author xingchi
* @date 2023-11-19 21:56:44
*/
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLink> implements ShortLinkService{

    @Autowired
    private ShortURLGenerateService shortURLGenerateService;

    @Override
    public String getLongUrlByShortId(String shortId) {

        if (!StringUtils.hasText(shortId)) {
            ExceptionWrap.cast("短链格式不正确");
        }

        return baseMapper.getLongUrlByShortId(shortId);
    }

    @Override
    public void storeShortURLMapping(String shortId, String longURL, String serviceName) {

        if (!StringUtils.hasText(longURL)) {
            ExceptionWrap.cast("长链不能为空");
        }

        if (!longURL.matches(RegexConstants.REGEX_URL)) {
            ExceptionWrap.cast("长链接格式不正确，请输入正确的长链地址");
        }

        // 保存短链与长链的映射关系
        ShortLink shortLink = new ShortLink();
        Long id = IdContextHolder.get();
        shortLink.setId(id);
        shortLink.setShortId(shortId);
        shortLink.setLongUrl(longURL);
        shortLink.setServiceName(serviceName);

        // TODO 后期需加入缓存
        this.save(shortLink);
    }

    @Override
    public String getLongURL(String shortId) {
        // TODO 后期改为从Redis中查询
        return this.getLongUrlByShortId(shortId);
    }

    @Override
    public void storeLongUrl(ShortLinkCreateDTO shortLinkCreateDTO) {

        String longUrl = shortLinkCreateDTO.getLongUrl();
        String shortId = shortURLGenerateService.generateShortURL(longUrl);

        String serviceName = shortLinkCreateDTO.getServiceName();
        serviceName = StringUtils.hasText(serviceName) ? DEFAULT_SERVICE_NAME : serviceName;

        this.storeShortURLMapping(shortId, longUrl, serviceName);
    }
}




