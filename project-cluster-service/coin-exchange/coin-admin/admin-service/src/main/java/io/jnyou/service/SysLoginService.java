package io.jnyou.service;

import io.jnyou.model.LoginResult;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SysLoginService
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface SysLoginService {
    /**
     * login method
     * @param username
     * @param password
     */
    LoginResult login(String username, String password);
}