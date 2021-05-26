package org.jnyou.mall.auth.biz;

import org.jnyou.common.vo.MemberResponseVo;
import org.jnyou.mall.auth.vo.UserLoginVo;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * ThirdLoginStrategy
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface ThirdLoginStrategy {

    MemberResponseVo strategyLogin(UserLoginVo userLoginVo);

    LoginEnum LOGIN_ENUM();
}
