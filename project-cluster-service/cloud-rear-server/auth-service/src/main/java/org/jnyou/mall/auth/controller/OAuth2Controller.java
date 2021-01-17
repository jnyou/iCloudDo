package org.jnyou.mall.auth.controller;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jnyou.common.utils.HttpUtils;
import org.jnyou.mall.auth.vo.SocialUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OAuth2Controller
 * 处理社交登录请求
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class OAuth2Controller {

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {
        // 根据code换取AccessToken  https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE
        Map<String,String> map = new HashMap<>();
        map.put("client_id","1229245080");
        map.put("client_secret","1ce8cd74348ec151f1e429186da434bf");
        map.put("grant_type","authorization_code");
        map.put("code",code);
        map.put("redirect_uri","http://auth.gmall.com/oauth2.0/weibo/success");
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "POST", new HashMap<String, String>(), new HashMap<String, String>(), map);
        if(response.getStatusLine().getStatusCode() == 200){
            // 获取到了accessToken
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
            // 判断该用户是登录还是注册

        } else {
            // 失败跳转到登录页面
            return "redirect:http://auth.gmall.com/login.html";
        }
        // 成功之后回到主页面
        return "redirect:http://gmall.com";
    }

}