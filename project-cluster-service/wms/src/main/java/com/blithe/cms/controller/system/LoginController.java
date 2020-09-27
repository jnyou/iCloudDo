package com.blithe.cms.controller.system;

import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.tools.HttpContextUtils;
import com.blithe.cms.common.tools.IpUtil;
import com.blithe.cms.log.SysLog;
import com.blithe.cms.pojo.system.Loginfo;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.system.LoginfoService;
import com.blithe.cms.service.system.SysUserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName LoginController
 * @Description: 登陆
 * @Author: 夏小颜
 * @Date: 14:55
 * @Version: 1.0
 **/

@RestController
@RequestMapping("login")
public class LoginController {

    private static final Log logger = LogFactory.getLog(LoginController.class);
    /**
     * session中的验证码
     */
    private static final String SHIRO_VERIFY_SESSION = "verifySessionCode";
    /**
     * 错误后的跳转地址
     */
    private String ERROR_CODE_URL = "login";
    /**
     * 成功后的跳转地址
     */
    private String SUCCESS_CODE_URL = "index";
    /**
     * 验证失败提示
     */
    private String ERROR_PASSWORD = "密码不正确";
    private String ERROR_ACCOUNT = "账户不存在";
    private String ERROR_STATUS = "状态不正常";
    private String ERROR_KAPTCHA = "验证码不正确";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private LoginfoService loginfoService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * check code and pwd
     */
    @SysLog("用户登陆认证")
    @PostMapping("login")
    public R checkAccount(@RequestParam("username") String name, @RequestParam("password") String pwd, @RequestParam("code") String code,
                            @RequestParam("rememberMe") boolean rememberMe){
        // 调用shiro提供得外部接口subject
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,pwd);

        // 获取session中的验证码
        String verCode = (String) currentUser.getSession().getAttribute(SHIRO_VERIFY_SESSION);
        if("".equals(code)||(!verCode.equals(code))){
            logger.error(ERROR_KAPTCHA);
            return  R.error(ERROR_KAPTCHA);
        }

        try {
            //主体提交登录请求到SecurityManager
//            token.setRememberMe(true);
            // 登陆成功 调用login方法
            currentUser.login(token);
//        }catch (IncorrectCredentialsException e){
//            System.out.println("密码不正确");
//        }catch (UnknownAccountException e){
//            System.out.println("账号不存在");
        }catch (AuthenticationException e){
            logger.error("用户名或者密码不正确");
            return R.error("用户名或者密码不正确");
        }
        if(currentUser.isAuthenticated()){
            logger.info("认证成功");
            // 从subject中获取用户身份
            SysUser sysUser = (SysUser) currentUser.getPrincipal();
            // 调用工具栏获得session并将用户信息存入session中
            HttpContextUtils.getHttpServletRequest().getSession().setAttribute("user",sysUser);
            // 登陆成功后将登陆信息记录到登陆日志中
            Loginfo loginfo = new Loginfo();
            loginfo.setLoginname(sysUser.getName()+"-"+sysUser.getLoginname());
            loginfo.setLoginip(IpUtil.getIpAddr(HttpContextUtils.getHttpServletRequest()));
            loginfo.setLogintime(new Date());
            loginfoService.insert(loginfo);
        }
        return R.ok();
    }


    /**
     * 退出登录
     */
    @GetMapping("logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return ERROR_CODE_URL;
    }

    /**
     * 获取验证码
     * @param response
     */
    @GetMapping("getCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] verByte = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到shiro的session中
            String createText = defaultKaptcha.createText();
            SecurityUtils.getSubject().getSession().setAttribute(SHIRO_VERIFY_SESSION,createText);
//            HttpContextUtils.getHttpServletRequest().getSession().setAttribute(SHIRO_VERIFY_SESSION,createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (IllegalArgumentException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        verByte = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(verByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 被踢出后跳转的页面
     */
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickOut() {
        return ERROR_CODE_URL;
    }

}