package org.jnyou.gmall.memberservice.exception;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * UsernameExistException
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class PhoneExistException extends RuntimeException{
    public PhoneExistException() {
        super("手机号已存在");
    }
}