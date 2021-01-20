package org.jnyou.mall.cart.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * UserVo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@ToString
@Data
public class UserInfoTo {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 临时用户的cookie值
     */
    private String userKey;
    /**
     * 是否存在临时用户
     */
    private boolean tempUser = false;

}