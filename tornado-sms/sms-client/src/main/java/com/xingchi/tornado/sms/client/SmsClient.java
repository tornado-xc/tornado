package com.xingchi.tornado.sms.client;

import com.xingchi.tornado.basic.Result;
import com.xingchi.tornado.sms.client.feign.SmsFeignClient;
import com.xingchi.tornado.sms.common.model.dto.NoticeDTO;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送客户端
 *
 * @author xingchi
 * @date 2023/11/3 20:28
 */
@Component
public class SmsClient {

    private final SmsFeignClient smsFeignClient;

    @Autowired
    public SmsClient(SmsFeignClient smsFeignClient) {
        this.smsFeignClient = smsFeignClient;
    }

    /**
     * 发送短信
     *
     * @param notice        发送短信元数据信息
     * @return              是否发送成功
     */
    boolean sendNote(NoticeDTO notice) {
        Result<Boolean> result = smsFeignClient.sendNote(notice);
        return BooleanUtils.isTrue(result.getSuccess());
    }

}
