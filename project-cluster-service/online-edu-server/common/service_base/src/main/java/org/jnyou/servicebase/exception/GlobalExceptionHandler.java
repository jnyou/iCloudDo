package org.jnyou.servicebase.exception;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.commonutils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: jnyou
 * @Description: 全局异常处理
 * @Date: 2020/5/30
 **/
@RestControllerAdvice // 此注解包括两个重要注解：@ControllerAdvice  and   @ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 包括：全局异常处理、特定异常处理、自定义异常处理
     */

    /**
     * 拦截所有异常 全局
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error().message(e.getMessage());
    }


    /**
     * 自定义异常
     */
    @ExceptionHandler(IsMeException.class)
    public R handleNmbcException(IsMeException e) {
        log.error(e.getMsg(), e);
        return R.error().code(e.getCode()).message(e.getMsg());
    }

    /**
     * 特定异常处理： 指定出现的特定异常类型
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

//    @ExceptionHandler(UnauthenticatedException.class)
//    public R handleUnauthenticatedException(UnauthenticatedException e) {
//        LOGGER.error(e.getMessage(), e);
//        return R.error("已注销，请重新登录系统");
//    }


//    @ExceptionHandler(AuthorizationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public R handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
//        log.error("请求:{}的时候出现错误：{}",request.getRequestURI(),e.getMessage());
//        log.error(e.getMessage(), e);
//        return R.error("没有权限，请联系管理员授权");
//    }


//    /**
//     *
//      拦截未授权页面
//     */
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    @ExceptionHandler(UnauthorizedException.class)
//    public String handleException1(UnauthorizedException e) {
//        log.debug(e.getMessage());
//        return "403";
//    }
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AuthorizationException.class)
//    public String handleException2(AuthorizationException e) {
//        log.debug(e.getMessage());
//        return "403";
//    }
}
