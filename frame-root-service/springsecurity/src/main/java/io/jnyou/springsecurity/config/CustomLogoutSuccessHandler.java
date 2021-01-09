package io.jnyou.springsecurity.config;

import cn.hutool.json.JSONUtil;
import io.jnyou.springsecurity.common.HttpResult;
import io.jnyou.springsecurity.constant.ConfigConstants;
import io.jnyou.springsecurity.service.RedisService;
import io.jnyou.springsecurity.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * GET: /logout
 * 注销操作
 * 清除用户token
 * 清除用户权限
 *
 * @author Jnyou
 * @version 1.0.0
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RedisService redisService;
    private final TokenManager tokenManager;

    public CustomLogoutSuccessHandler(RedisService redisService, TokenManager tokenManager) {
        this.redisService = redisService;
        this.tokenManager = tokenManager;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("退出，清理用户token");

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring("Bearer ".length());
            String userId = tokenManager.getUserFromToken(authToken);
//            String userId = TokenUtil.parseToken(authToken);
            redisService.delete(ConfigConstants.USER_TOKEN_PREFIX + userId);
            redisService.delete(ConfigConstants.USER_PERMISSION_PREFIX + userId);
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(HttpResult.success()));
    }
}
