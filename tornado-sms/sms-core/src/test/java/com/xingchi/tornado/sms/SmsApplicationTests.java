package com.xingchi.tornado.sms;

import com.xingchi.tornado.sms.service.NoteTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaoya
 * @date 2023/6/14 13:51
 * @modified xiaoya
 */
@SpringBootTest
public class SmsApplicationTests {

    @Autowired
    private NoteTemplateService noteTemplateService;

}
