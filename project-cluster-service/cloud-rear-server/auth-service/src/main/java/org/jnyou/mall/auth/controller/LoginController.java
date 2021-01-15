package org.jnyou.mall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import org.jnyou.common.constant.AuthServerConstant;
import org.jnyou.common.exception.BizCodeEnume;
import org.jnyou.common.utils.R;
import org.jnyou.mall.auth.feign.MemberFeignClient;
import org.jnyou.mall.auth.feign.ThridPartyFeignClient;
import org.jnyou.mall.auth.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.stream.Collectors;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Controller
public class LoginController {

    @Autowired
    ThridPartyFeignClient thridPartyFeignClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MemberFeignClient memberFeignClient;

    @GetMapping("/sendSms")
    @ResponseBody
    public R sendSms(@RequestParam("phone") String phone) {

        // 1、接口防刷。同一个手机号在60秒内再次放松验证码
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if(!StringUtils.isEmpty(redisCode)){
            long redisTime = Long.parseLong(redisCode.split("_")[1]);
            // 2、60s内不能再发
            if(System.currentTimeMillis() - redisTime < 60000){
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(),BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        String code = UUID.randomUUID().toString().substring(0, 5);
        String redisCodeSave = code + "_" + System.currentTimeMillis();
        // 2、验证码过期时间，再次校验 Redis过期 key:phone,value:code
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, redisCodeSave, 10, TimeUnit.MINUTES);

        thridPartyFeignClient.sendCode(phone, code);
        return R.ok();
    }

    /**
     * RedirectAttributes ：模拟重定向携带数据返回
     * 重定向携带数据，利用session原理，将数据一次放在session中
     *
     * TODO 分布式session 的问题
     * @param vo
     * @param result
     * @param redirectAttributes
     * @Author JnYou
     */
    @PostMapping("regist")
    public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
//            model.addAttribute("errors",errors);
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gmall.com/reg.html";
        }

        // 1、校验验证码
        String code = vo.getCode();
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        // 模拟都通过  !StringUtils.isEmpty(redisCode)
        if(true) {
            // code.equals(redisCode.split("-")[0])
            if(true) {
                // 验证码通过 , 删除验证码（令牌机制）
//                stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                // 真正调用远程服务进行注册
                R r = memberFeignClient.regist(vo);
                if(r.getCode() == 0){
                    return "redirect:http://auth.gmall.com/login.html";
                } else {
                    Map<String, String> errors = new HashMap<>(124);
                    errors.put("msg",r.getData(new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.gmall.com/reg.html";
                }
            } else {
                Map<String, String> errors = new HashMap<>(124);
                errors.put("code","验证码错误");
                redirectAttributes.addFlashAttribute("errors",errors);
                // 校验出错误，转发到注册页
                return "redirect:http://auth.gmall.com/reg.html";
            }
        } else {
            Map<String, String> errors = new HashMap<>(124);
            errors.put("code","验证码错误");
            redirectAttributes.addFlashAttribute("errors",errors);
            // 校验出错误，转发到注册页
            return "redirect:http://auth.gmall.com/reg.html";
        }
    }


}