package io.jnyou.controller;

import io.jnyou.geetest.GeetestLib;
import io.jnyou.geetest.GeetestLibResult;
import io.jnyou.model.R;
import io.jnyou.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "极域验证的数据接口")
@RequestMapping("/gt")
public class GeetestController {

    @Autowired
    private GeetestLib geetestLib;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/register")
    @ApiOperation(value = "极验获取第一次数据包----注册")
    @ApiImplicitParam(name = "uuid", value = "用户的标识")
    public R<String> register(String uuid) {
        String digestmod = "md5";
        // 构造极验数据包
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("digestmod", digestmod);
        paramMap.put("user_id", uuid);
        paramMap.put("client_type", "web");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        paramMap.put("ip_address", IpUtil.getIpAddr(requestAttributes.getRequest()));
        GeetestLibResult result = geetestLib.register(digestmod, paramMap);
        // 将结果状态写到session中，此处register接口存入session，后续validate接口会取出使用
        // 注意，此demo应用的session是单机模式，格外注意分布式环境下session的应用
//        request.getSession().setAttribute(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY, result.getStatus());
        redisTemplate.opsForValue().set(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY + ":" + uuid, result.getStatus());
//        request.getSession().setAttribute("userId", userId);
        redisTemplate.opsForValue().set(GeetestLib.GEETEST_SERVER_USER_KEY+ ":" + uuid, uuid);
        // 注意，不要更改返回的结构和值类型
        return R.ok(result.getData());
    }

}