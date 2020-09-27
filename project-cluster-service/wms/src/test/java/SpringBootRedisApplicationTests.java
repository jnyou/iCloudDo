import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.common.tools.IpUtil;
import com.blithe.cms.pojo.system.Loginfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName SpringBootRedisApplicationTests
 * @Description: 测试缓存
 * @Author: 夏小颜
 * @Date: 23:15
 * @Version: 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRedisApplicationTests.class)
@ComponentScan("com.blithe.cms")
public class SpringBootRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testObj(){


        //这里相当于redis对String类型的set操作
//        redisTemplate.opsForValue().set("test","helloworld");
        //这里相当于redis对String类型的get操作
//        String test = (String)redisTemplate.opsForValue().get("test");
//        System.out.println(test);
        Loginfo loginfo = new Loginfo();
        loginfo.setLoginname("小剑");
        loginfo.setLogintime(new Date());
        loginfo.setLoginip(IpUtil.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        redisTemplate.opsForValue().set("loginInfo_",loginfo);


        /**
         * 在上面这个例子中我们使用redisTemplate调用了opsForValue会得到一个ValueOperations操作。
         * 这个是专门操作String类型的数据，所以里面的键值对中键为String，而值是我们的User。
         * 当然redisTemplate还为我们提供了下面几个。
         */
//        Boolean exists = (Boolean) redisTemplate.opsForValue().get("loginInfo_");
//        System.out.println("redis是否存在key" + exists);
        Loginfo loginInfo_ = (Loginfo)  redisTemplate.opsForValue().get("loginInfo_");
        System.out.println("从redis取出对象" + loginInfo_.toString());


    }
}