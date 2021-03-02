package io.jnyou.auth.service.impl;

import io.jnyou.auth.constant.LoginConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * UserServiceDetailsServiceImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
public class UserServiceDetailsServiceImpl implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 区分管理员还是普通会员
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("请添加login_type参数");
        }
        String grantType = requestAttributes.getRequest().getParameter("grant_type");
        UserDetails userDetails = null;
        try {
            if (LoginConstant.REFRESH_TOKEN.equals(grantType.toUpperCase())) {
                // 为refresh_token 时，需要将id->username
                username = adjustUsername(username, loginType);
            }
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    // 管理员登录
                    userDetails = loadAdminUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    // 会员登录
                    userDetails = loadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("用户名" + username + "不存在.");
        }
        return userDetails;
    }

    /**
     * 纠正在refresh 场景下的登录问题
     * 纠正用户的名称，存储的时候将用户的id当做username给userdetail；
     *
     * @param username
     * @param loginType
     */
    private String adjustUsername(String username, String loginType) {

        if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;

    }

    /**
     * 会员登录
     *
     * @param username
     */
    private UserDetails loadMemberUserByUsername(String username) {
        jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, (ResultSet rs, int rowNum) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户名" + username + "不存在.");
            }
            long id = rs.getLong("id");
            String password = rs.getString("password");
            int status = rs.getInt("status");

            return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }, username, username);
        return null;
    }


    /**
     * 会员登录
     *
     * @param username
     */
    private UserDetails loadAdminUserByUsername(String username) {
        // 1、使用用户名查询用户信息
        User user = jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (ResultSet rs, int rowNum) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户名" + username + "不存在.");
            }
            // 用户的id
            long id = rs.getLong("id");
            // 用户的密码
            String password = rs.getString("password");
            // 用户状态
            int status = rs.getInt("status");

            // 3、封装UserDetails对象，返回
            return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    // 2、查询当前用户的权限信息
                    getSysUserPermissions(id)
            );
        }, username);
        return user;
    }

    private Collection<? extends GrantedAuthority> getSysUserPermissions(long id) {
        // 判断超级管理员还是普通管理员
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null;
        if (LoginConstant.ADMIN_CODE.equals(roleCode)) {
            // 超级管理员的权限列表
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            // 普通管理员的权限列表
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        }
        // 去重
        return permissions.stream().distinct().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toSet());
    }
}