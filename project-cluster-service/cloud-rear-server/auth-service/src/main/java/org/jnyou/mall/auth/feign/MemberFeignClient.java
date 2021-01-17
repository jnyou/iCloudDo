package org.jnyou.mall.auth.feign;

import org.jnyou.common.utils.R;
import org.jnyou.mall.auth.vo.SocialUser;
import org.jnyou.mall.auth.vo.UserLoginVo;
import org.jnyou.mall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MemberFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("member-service")
public interface MemberFeignClient {

    @PostMapping("/member/member/regist")
    R regist(@RequestBody UserRegisterVo vo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping("/member/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser user);


}
