package org.jnyou.mall.auth.biz.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.jnyou.common.utils.HttpUtils;
import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.mall.auth.biz.LoginEnum;
import org.jnyou.mall.auth.biz.ThirdLoginStrategy;
import org.jnyou.mall.auth.vo.UserLoginVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * WeiboLogin
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
@Slf4j
public class WeiboLogin implements ThirdLoginStrategy {
    @Override
    public MemberResponseVo strategyLogin(UserLoginVo userLoginVo) {
        // TODO 微博登录逻辑
        log.info("登录名{}，登录密码{}",userLoginVo.getLoginAccount(),userLoginVo.getPassword());
        return null;
    }

    @Override
    public LoginEnum LOGIN_ENUM() {
        return LoginEnum.WEIBO_LOGIN;
    }
}