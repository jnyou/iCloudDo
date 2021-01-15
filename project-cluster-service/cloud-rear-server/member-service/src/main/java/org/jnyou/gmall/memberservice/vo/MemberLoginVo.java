package org.jnyou.gmall.memberservice.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MemberLoginVo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class MemberLoginVo {

    @NotEmpty(message = "用户名不能为空")
    private String loginAccount;
    @NotEmpty(message = "密码不能为空")
    private String password;

}