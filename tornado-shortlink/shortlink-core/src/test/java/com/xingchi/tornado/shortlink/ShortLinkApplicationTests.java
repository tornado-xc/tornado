package com.xingchi.tornado.shortlink;

import com.xingchi.tornado.codec.Base62;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试类
 *
 * @author xiaoya
 * @date 2023/7/21
 */
@SpringBootTest
public class ShortLinkApplicationTests {

    @Autowired
    private UniqueCodeClient uniqueCodeClient;

    @Test
    public void testBase62() {
        System.out.println("Base62.encode(uniqueCodeClient.redisId()) = " + Base62.encode(uniqueCodeClient.redisId()));
        System.out.println("Base62.encode(uniqueCodeClient.redisId()) = " + Base62.encode(uniqueCodeClient.snowflakeId()));
    }

}
