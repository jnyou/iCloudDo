package io.jnyou.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import io.jnyou.model.WebLog;
import io.jnyou.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    /**
     * 通过username 查询webLog
     *
     * @param username
     * @return
     */
    @Override
    @Cached(name = "io.jnyou.service.impl.TestServiceImpl:", key = "#username", cacheType = CacheType.BOTH)
    public WebLog get(String username) {
        WebLog webLog = WebLog.builder()
                .username(username)
                .result("ok")
                .build();
        return webLog;
    }
}