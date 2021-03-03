package io.jnyou.service;

import io.jnyou.model.WebLog;

public interface TestService {

    /**
     * 通过username 查询weblog
     *
     */
    WebLog get(String username) ;
}