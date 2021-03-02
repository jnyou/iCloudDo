package io.jnyou.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * AuthorizationServerConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 配置第三方客户端
     *
     * @param clients
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 第三方客户端的名称
                .withClient("coin-api")
                // 第三方客户端的秘钥
                .secret(passwordEncoder.encode("coin-secret"))
                // grant-type:refresh_token ; 授权类型
                .authorizedGrantTypes("password", "refresh_token")
                // 第三方客户端的授权范围
                .scopes("all")
                // token的有效期
                .accessTokenValiditySeconds(7 * 24 * 3600)
                // refresh_token的有效期
                .refreshTokenValiditySeconds(30 * 24 * 3600)

                // 以下是OAuth2提供的应用之间内部获取token的方式
                .and()
                .withClient("inside-app")
                .secret(passwordEncoder.encode("inside-secret"))
                .secret("all")
                // grant-type:client_credentials ; 授权类型
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds(7 * 24 * 3600);
        ;
        super.configure(clients);
    }

    /**
     * 配置验证管理器：userdetailService
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // 使用jwt进行存储
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter());
        super.configure(endpoints);
    }

    private TokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        // 读取classpath 下面的密钥文件
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
        // 获取KeyStoreFactory
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "coinexchange".toCharArray());
        // 给JwtAccessTokenConverter 设置一个密钥对
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange", "coinexchange".toCharArray()));
        return tokenConverter;
    }

}