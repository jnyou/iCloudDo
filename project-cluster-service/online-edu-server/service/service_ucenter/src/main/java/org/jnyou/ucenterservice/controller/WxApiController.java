package org.jnyou.ucenterservice.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.commonutils.JwtUtils;
import org.jnyou.servicebase.exception.IsMeException;
import org.jnyou.commonutils.entity.UcenterMember;
import org.jnyou.ucenterservice.service.UcenterMemberService;
import org.jnyou.ucenterservice.utils.ConstantPropertiesUtil;
import org.jnyou.ucenterservice.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @ClassName WxApiController
 * @Description: 微信扫码登陆：大致步骤：请求微信文档提供的固定url获取微信登陆二维码，扫码后跳转到本地的回调方法（callback），获得code，
 * 再回调方法中通过code、id、密钥获取accessTokenUrl，通过httpclient发送GET请求这个accessTokenUrl，获取返回结果json字符串，
 * 使用gson转换成map格式，获取结果集中的access_token和openid；再通过access_token和openid这两个参数请求微信提供的url进行获取用户信息，
 * 通过gson转换获取用户昵称、头像等存入数据库中。。。
 * @Author: jnyou
 **/
@Controller
@RequestMapping("api/ucenter/wx")
@Slf4j
public class WxApiController {


    @Autowired
    private UcenterMemberService memberService;

    /**
     * 根据官网文档生成微信扫码登陆的二维码
     *
     * @return
     */
    @GetMapping("login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl  %s参数占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址进行编码
        // 获取业务服务器重定向地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            // url编码
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IsMeException(-1, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        //为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        String state = "imhelen";
        log.info("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }

    /**
     * 测试回调获取用户信息
     *
     * @param code
     * @param state
     * @param session
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //得到授权临时票据code
        log.info("code = " + code);
        log.info("state = " + state);

        // 从redis中将state获取出来，和当前传入的state作比较
        // 如果一致则放行，如果不一致则抛出异常：非法访问

        // 向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        // 请求路径accessTokenUrl
        String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        log.info("accessTokenUrl====" + accessTokenUrl);
        // 使用httpclient发送请求，得到返回结果
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            log.info("accessToken=============" + result);
        } catch (Exception e) {
            throw new IsMeException(-1, "获取access_token失败");
        }
        // json字符串 accessToken============={"access_token":"34_NYg-DpsIkG1fw3cfaq7vFiyVVbe1e-DuN1qnQ7CYXRIT_UMPkiXNfzHXOMQUtzSx7LFYK4HQmdM2hxgM55PLrR0iOYjLKJmXYTveFnJWWuI","expires_in":7200,"refresh_token":"34_vACdEUuu-6fcPMcYxs5iDQ2Subi485AjzewGUHV7lC-nwpSFiXPcs7OCXs2VlU8-JdWxV45LJ-JuNJ3DULV8yvV4dvfjlmELgNEGU9abg3U","openid":"o3_SC5-822XmQdwCDFYQkMAXHY78","scope":"snsapi_login","unionid":"oWgGz1FDH1k93xz9uvK1B1HPKa4c"}
        log.info("accessToken=============" + result);
        // 通过gson将结果集转换成map获取其中的access_token，openid
        Gson gson = new Gson();
        HashMap<String, Object> mapAccessToken = gson.fromJson(result, HashMap.class);
        String accessToken = (String) mapAccessToken.get("access_token");
        String openid = (String) mapAccessToken.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
        UcenterMember member = memberService.getByOpenid(openid);
        if (null == member) {

            log.info("新用户注册，保存信息");

            // 通过accessToken和openid访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            // 发送请求
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                log.info("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new IsMeException(-1, "获取用户信息失败");
            }
            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        // 使用JWT根据用户对象生成token字符串  //因为端口号不同存在跨域问题，cookie不能跨域，所以这里使用url重写
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        // 返回首页面
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }

}