package org.jnyou.mall.auth;

import org.jnyou.mall.auth.biz.ThirdLoginStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

    @Autowired
    ThirdLoginStrategy strategy;

    @Test
    void contextLoads() {
        System.out.println(strategy);
    }

}
