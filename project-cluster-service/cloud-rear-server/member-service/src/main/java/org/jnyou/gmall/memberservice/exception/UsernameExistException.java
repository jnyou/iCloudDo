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
public class UsernameExistException extends RuntimeException{
    public UsernameExistException() {
        super("用户名已存在");
    }
}