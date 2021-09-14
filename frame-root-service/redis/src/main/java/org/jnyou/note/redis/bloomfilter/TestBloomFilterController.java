package org.jnyou.note.redis.bloomfilter;

import cn.hutool.core.date.StopWatch;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.hash.Funnel;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TestBloomFilterController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@RequestMapping("/api/v1")
@RestController
public class TestBloomFilterController {

    Logger logger = LoggerFactory.getLogger(TestBloomFilterController.class);

    @Autowired
    RedisBloomFilter redisBloomFilter;
    @Autowired
    private BloomFilterHelper bloomFilterHelper;
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String redisKey = "task:core:form:data:distinct:batch610ba36ff3a95e28b8dd4251:2";

//    @Value("${lua.luaPath}")
//    private String luaPath;//lua脚本路径

    @PostMapping(value = "/addEmailToBloom", produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody String params) {
        ResponseEntity<String> response = null;
        String returnResultStr;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject requestJsonObj = JSON.parseObject(params);
            UserVO inputUser = getUserFromJson(requestJsonObj);
            BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>)
                    (from,into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
            redisBloomFilter.addByBloomFilter(myBloomFilterHelper, redisKey, inputUser.getEmail());
            result.put("code", HttpStatus.OK.value());
            result.put("message", "add into bloomFilter successfully");
            result.put("email", inputUser.getEmail());
            returnResultStr = JSON.toJSONString(result);
            logger.info("returnResultStr======>" + returnResultStr);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("add a new product with error: " + e.getMessage(), e);
            result.put("message", "add a new product with error: " + e.getMessage());
            returnResultStr = JSON.toJSONString(result);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping(value = "/checkEmailInBloom", produces = "application/json")
    public ResponseEntity<String> findEmailInBloom(@RequestBody String params) {
        ResponseEntity<String> response = null;
        String returnResultStr;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject requestJsonObj = JSON.parseObject(params);
            UserVO inputUser = getUserFromJson(requestJsonObj);
            BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,
                                                                                                      into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
            boolean answer = redisBloomFilter.includeByBloomFilter(myBloomFilterHelper, redisKey,
                    inputUser.getEmail());
            logger.info("answer=====" + answer);
            result.put("code", HttpStatus.OK.value());
            result.put("email", inputUser.getEmail());
            result.put("exist", answer);
            returnResultStr = JSON.toJSONString(result);
            logger.info("returnResultStr======>" + returnResultStr);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("add a new product with error: " + e.getMessage(), e);
            result.put("message", "add a new product with error: " + e.getMessage());
            returnResultStr = JSON.toJSONString(result);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * 计时工具类
     */
    public static Stopwatch stopwatch = Stopwatch.createUnstarted();
    @GetMapping(value = "/checkEmailInBloom1")
    public ResponseEntity<String> checkEmailInBloom() {
        stopwatch.stop();
        List<String> busilist = buildData();
        //计时开始
        stopwatch.start();
        List<String> repeat = new ArrayList<>();
        BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,
                                                                                                  into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://192.168.188.152:7004");
//        config.useSingleServer().setPassword("shield@2019");
//        //构造Redisson
//        RedissonClient redisson = Redisson.create(config);
//        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");
//        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
//        bloomFilter.tryInit(100000000L,0.03);
//        //将号码10086插入到布隆过滤器中
//        bloomFilter.add("10086");
//
//        //判断下面号码是否在布隆过滤器中
//        System.out.println(bloomFilter.contains("123456"));//false
//        System.out.println(bloomFilter.contains("10086"));//true

//        List keys = new ArrayList<>();
//        keys.add(redisKey);
//        RedisScript<Boolean> redisScript = getRedisScript();
//        busilist.stream().parallel().forEach(v -> {
//            Boolean result = stringRedisTemplate.execute(redisScript, keys, v);
//            if(result){
//                // 重复
//                repeat.add(v);
//            }
//        });
        busilist.stream().parallel().forEach(v -> {
            if(redisBloomFilter.includeByBloomFilter(myBloomFilterHelper, redisKey,
                    v)){
                // 重复
                repeat.add(v);
            }
        });
        logger.info("重复的数量：{}",repeat.size());

        //计时结束
        stopwatch.stop();
        logger.info("list集合测试，判断该元素集合中是否存在用时:{}", stopwatch.elapsed(MILLISECONDS));
        stopwatch.reset();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static List<String> buildData(){
        List<String> email = new ArrayList<>();
        email.add("ogpmlpenhchcpcjg@163.com");
        email.add("kjabnbdpfhcnokii@163.com");
        for (int i=0;i<50000;i++) {
            email.add("kjabnbdpfhcnoki"+i+"@163.com");
            if(i == 45000){
                email.add("leghpichiagileeh@163.com");
            }
        }
        return email;
    }

    private UserVO getUserFromJson(JSONObject requestObj) {
        String userName = requestObj.getString("username");
        String userAddress = requestObj.getString("address");
        String userEmail = requestObj.getString("email");
        String phone = requestObj.getString("phone");
        int userAge = requestObj.getInteger("age");
        UserVO u = new UserVO();
        u.setName(userName);
        u.setAge(userAge);
        u.setEmail(userEmail);
        u.setAddress(userAddress);
        u.setPhone("18296557705");
        return u;

    }


    private static RedisScript<Boolean> getRedisScript() {

        RedisScript script = null;
        if (script != null) {
            return script;
        }

        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("redis/bloomFilter-exist.lua"));
        String str = null;
        try {
            str = scriptSource.getScriptAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        script = RedisScript.of(str, Boolean.class);
        return script;
    }

}