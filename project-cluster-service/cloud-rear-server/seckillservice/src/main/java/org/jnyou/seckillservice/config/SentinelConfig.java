package org.jnyou.seckillservice.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SentinelConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Component
public class SentinelConfig implements RequestOriginParser {
    private static final String ALLOW = "Allow";

    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getHeader(ALLOW);
    }
}