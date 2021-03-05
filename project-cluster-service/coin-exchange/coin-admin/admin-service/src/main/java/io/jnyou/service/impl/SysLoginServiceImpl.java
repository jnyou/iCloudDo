package io.jnyou.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.jnyou.domain.SysMenu;
import io.jnyou.feign.Oauth2FeignClient;
import io.jnyou.model.JwtToken;
import io.jnyou.model.LoginResult;
import io.jnyou.service.SysLoginService;
import io.jnyou.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SysLoginServiceImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Slf4j
@Service
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    Oauth2FeignClient oauth2FeignClient;

    @Autowired
    SysMenuService sysMenuService;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public LoginResult login(String username, String password) {
        /**
         * String token 、 List<SysMenu> menus 、 List<SimpleGrantedAuthority> authorities
         * @param username
         * @param password
         * @Author JnYou
         */
        // 1、获取token
        ResponseEntity<JwtToken> responseEntity = oauth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ApiErrorCode.FAILED);
        }
        JwtToken jwtToken = responseEntity.getBody();
        log.info("远程调用授权服务成功，获取的token为{}", JSON.toJSONString(jwtToken, true));
        String accessToken = jwtToken.getAccessToken();

        // 2、查询菜单
        Jwt jwt = JwtHelper.decode(accessToken);
        String jwtJsonStr = jwt.getClaims();
        JSONObject jwtJson = JSON.parseObject(jwtJsonStr);
        Long userId = Long.valueOf(jwtJson.getString("user_name"));
        // 通过用户id获取用户菜单
        List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);

        // 3、查询权限
        JSONArray jsonArray = jwtJson.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorities = jsonArray.stream().map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString())).collect(Collectors.toList());
        // 保存token到redis中
        redisTemplate.opsForValue().set(accessToken, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return new LoginResult(jwtToken.getTokenType() + " " + accessToken, menus, authorities);
    }
}