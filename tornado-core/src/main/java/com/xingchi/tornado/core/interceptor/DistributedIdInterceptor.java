package com.xingchi.tornado.core.interceptor;

import com.xingchi.tornado.core.context.BaseContextHolder;
import com.xingchi.tornado.core.context.IdContextHolder;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分布式id拦截器
 *
 * @author xiaoya
 * @date 2023/6/19 17:24
 * @modified xiaoya
 */
public class DistributedIdInterceptor implements HandlerInterceptor {

    private final UniqueCodeClient uniqueCodeClient;

    public DistributedIdInterceptor(UniqueCodeClient uniqueCodeClient) {
        this.uniqueCodeClient = uniqueCodeClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<Long> list = uniqueCodeClient.snowflakeIds(10);
        IdContextHolder.setUniqueCodeClient(uniqueCodeClient);
        IdContextHolder.set(list);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHolder.remove();
        IdContextHolder.remove();
    }

}
