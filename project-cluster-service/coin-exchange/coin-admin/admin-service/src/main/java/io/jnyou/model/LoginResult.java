package io.jnyou.model;

import io.jnyou.domain.SysMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * LoginResult
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Api(tags = "登录的结果")
public class LoginResult {
    /**
     * 登录产生的token
     */
    @ApiModelProperty(value = "登录成功的token，来着我们的authorization-server 里面的")
    private String token ;

    /**
     * 前端的菜单数据
     */
    @ApiModelProperty(value = "前端的菜单数据")
    private List<SysMenu> menus ;

    /**
     * 权限数据
     */
    @ApiModelProperty(value = "权限数据")
    private List<SimpleGrantedAuthority> authorities ;

}