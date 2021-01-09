package io.jnyou.springsecurity.details;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author Jnyou
 * @version 1.0.0
 */
@Slf4j
@Data
public class SecurityUserDetails implements UserDetails {

    private Long userId;

    private String username;

    private String password;

    private String token;
    /**
     * 当前用户权限
     */
    private List<String> permissions = Collections.emptyList();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("生成权限");
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.permissions.forEach(o -> authorities.add(new SimpleGrantedAuthority(o)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
