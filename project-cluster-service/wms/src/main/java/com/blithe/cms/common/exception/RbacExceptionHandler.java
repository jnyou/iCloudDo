package com.blithe.cms.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jnyou
 * @Description: 全局异常处理
 **/
@Slf4j                  /** use lombok loggin **/
@RestControllerAdvice  /**  此注解包括两个重要注解：@ControllerAdvice  and   @ResponseBody **/
public class RbacExceptionHandler {
//    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ResponseBody
    @ExceptionHandler(RbacException.class)
    public R handleNmbcException(RbacException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public R handleUnauthenticatedException(UnauthenticatedException e) {
        log.error(e.getMessage(), e);
        return R.error("已注销，请重新登录系统");
    }


    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
        log.error("请求:{}的时候出现错误：{}",request.getRequestURI(),e.getMessage());
        log.error(e.getMessage(), e);
        return R.error("没有权限，请联系管理员授权");
    }

    /**
     * 拦截所有异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }
}
