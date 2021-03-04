package io.jnyou.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * JwtToken
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class JwtToken {

    /**
     * access_token
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 过期时间
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    /**
     * token的范围
     */
    private String scope;

    /**
     * 颁发的凭证
     */
    private String jti;

}