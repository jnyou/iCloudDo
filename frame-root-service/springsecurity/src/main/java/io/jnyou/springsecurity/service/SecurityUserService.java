package io.jnyou.springsecurity.service;

import io.jnyou.springsecurity.constant.ConfigConstants;
import io.jnyou.springsecurity.details.SecurityUserDetails;
import io.jnyou.springsecurity.entity.User;
import io.jnyou.springsecurity.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author Jnyou
 * @version 1.0.0
 */
@Slf4j
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final RedisService redisService;

    public SecurityUserService(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    /**
     * 登录校验：根据用户名获取用户信息。
     * <p>
     * 生成Token并放入Redis中
     * 获取所有的权限并放入Redis中
     * <p>
     * 校验Token的时候直接从Redis中获取
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("根据用户名【{}】获取用户信息", username);
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setUserId(user.getId());
        securityUserDetails.setUsername(user.getUsername());
        securityUserDetails.setPassword(user.getPassword());

        return securityUserDetails;
    }

    /**
     * 校验token
     * 重置token 有效时间
     * 重置权限有效时间
     */
    public UserDetails loadUserForAuthorization(String userId) {
        // 从Redis中获取token
        String redisToken = redisService.get(ConfigConstants.USER_TOKEN_PREFIX + userId);
        if (redisToken == null) {
            return null;
        }

        redisService.expire(ConfigConstants.USER_TOKEN_PREFIX + userId, ConfigConstants.DEFAULT_TOKEN_INVALID_TIME);
        redisService.expire(ConfigConstants.USER_PERMISSION_PREFIX + userId, ConfigConstants.DEFAULT_TOKEN_INVALID_TIME);

        SecurityUserDetails user = new SecurityUserDetails();
        user.setUserId(Long.parseLong(userId));
        user.setToken(redisToken);
        return user;
    }
}
