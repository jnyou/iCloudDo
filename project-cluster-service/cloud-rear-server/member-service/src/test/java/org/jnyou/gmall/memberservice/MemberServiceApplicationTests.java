package org.jnyou.gmall.memberservice;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class MemberServiceApplicationTests {

    @Test
    void contextLoads() {
        // md5加密
        String s = DigestUtils.md5Hex("123456");
        System.out.println(s);

        // md5盐值加密
        Md5Crypt.md5Crypt("123456".getBytes());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
//        passwordEncoder.matches("",""); 匹配
        System.out.println(encode);

    }

}
