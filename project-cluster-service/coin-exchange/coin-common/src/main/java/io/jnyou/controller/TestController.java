package io.jnyou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jnyou.model.R;
import io.jnyou.model.WebLog;
import io.jnyou.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(tags = "CoinCommon里面测试的接口")
public class TestController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    TestService testService;

    @GetMapping("/common/test")
    @ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数1", dataType = "String", paramType = "query", example = "paramValue"),
            @ApiImplicitParam(name = "param1", value = "参数2", dataType = "String", paramType = "query", example = "paramValue")
    })
    public R<String> testMethod(String param, String param1) {

        return R.ok("ok");
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

    @GetMapping("/common/date")
    @ApiOperation(value = "日志格式化测试", authorizations = {@Authorization("Authorization")})
    public R<Date> testMethod() {
        return R.ok(new Date());
    }


    @GetMapping("/jetcache/test")
    @ApiOperation(value = "jetcache缓存的测试", authorizations = {@Authorization("Authorization")})
    public R<String> testJetCache(String username) {
        WebLog webLog = testService.get(username);
        System.out.println(webLog);
        return R.ok("OK");
    }

    @GetMapping("/redis/test")
    @ApiOperation(value = "redis测试", authorizations = {@Authorization("Authorization")})
    public R<String> testRedis() {
        WebLog webLog = WebLog.builder()
                .result("ok")
                .method("io.jnyou.domain.WebLog.testRedis")
                .username("1110")
                .build();
        redisTemplate.opsForValue().set("io.jnyou.domain.WebLog", webLog);
        return R.ok("OK");
    }

}