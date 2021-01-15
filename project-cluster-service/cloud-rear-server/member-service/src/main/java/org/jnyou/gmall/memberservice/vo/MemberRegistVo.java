package org.jnyou.gmall.memberservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MemberRegistVo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class MemberRegistVo {

    private String userName;

    private String password;

    private String phone;


}