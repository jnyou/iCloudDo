package org.jnyou.mall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.common.utils.HttpUtils;
import org.jnyou.common.utils.R;
import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.mall.auth.feign.MemberFeignClient;
import org.jnyou.mall.auth.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
@Slf4j
@Controller
public class OAuth2Controller {

    @Autowired
    MemberFeignClient memberFeignClient;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {
        // 根据code换取AccessToken  https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE
        Map<String,String> map = new HashMap<>();
        map.put("client_id","1229245080");
        map.put("client_secret","1ce8cd74348ec151f1e429186da434bf");
        map.put("grant_type","authorization_code");
        map.put("code",code);
        map.put("redirect_uri","http://auth.gmall.com/oauth2.0/weibo/success");
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "POST", new HashMap<String, String>(), new HashMap<String, String>(), map);
        Map<String, String> errors = new HashMap<>();
        if(response.getStatusLine().getStatusCode() == 200){
            // 获取到了accessToken
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
            // 判断该用户是登录还是注册
            R r = memberFeignClient.oauthLogin(socialUser);
            //2.1 远程调用成功，返回首页并携带用户信息
            if (r.getCode() == 0) {
                String jsonString = JSON.toJSONString(r.get("memberEntity"));
                System.out.println("----------------"+jsonString);
                MemberResponseVo memberResponseVo = JSON.parseObject(jsonString, new TypeReference<MemberResponseVo>() {
                });
                log.info("登录成功：用户：{}",memberResponseVo);
                session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
                // 成功之后回到主页面
                return "redirect:http://gmall.com";
            }else {
                //2.2 否则返回登录页
                errors.put("msg", "登录失败，请重试");
                session.setAttribute("errors", errors);
                return "redirect:http://auth.gmall.com/login.html";
            }
        } else {
            // 失败跳转到登录页面
            errors.put("msg", "获得第三方授权失败，请重试");
            session.setAttribute("errors", errors);
            return "redirect:http://auth.gmall.com/login.html";
        }
    }

}