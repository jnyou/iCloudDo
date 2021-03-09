package io.jnyou.feign;

import io.jnyou.config.FeignConfig;
import io.jnyou.model.JwtToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OAuth2FeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient(value = "authorization-server",configuration = FeignConfig.class)
public interface OAuth2FeignClient {
    /**
     *
     * 登录认证
     * @param username
     * @param password
     */
    @PostMapping("/oauth/token")
    ResponseEntity<JwtToken> getToken(
            @RequestParam("grant_type") String grantType, // 授权类型
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("login_type") String loginType, // 登录类型
            @RequestHeader("Authorization") String basicToken // 由第三方客户端信息加密出现的值
    );

}