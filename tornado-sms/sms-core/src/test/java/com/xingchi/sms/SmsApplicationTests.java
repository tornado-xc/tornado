package com.xingchi.sms;

import com.xingchi.sms.model.NoteTemplate;
import com.xingchi.sms.service.NoteTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
