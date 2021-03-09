package io.jnyou.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * JwtCheckFilter
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Value("${no.require.urls:/admin/login,/user/gt/register,/user/login}")
    private Set<String> noRequireTokenUris;

    /**
     * 过滤器拦截用户的请求后做啥
     *
     * @param exchange
     * @param chain
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1、该接口是够需要token才能访问
        if (!isRequire(exchange)) {
            // 不需要token，直接放行
            return chain.filter(exchange);
        }
        // 2、需要的话取出用户token
        String token = getUserToken(exchange);
        // 3、判断用户的token是否有效
        if (StringUtils.isEmpty(token)) {
            return buildNoAuthorizationResult(exchange);
        }
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (null != aBoolean & aBoolean) {
            // token有效，放行
            return chain.filter(exchange);
        }
        return buildNoAuthorizationResult(exchange);
    }

    /**
     * 给用户响应一个没有token的错误
     *
     * @param exchange
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "NoAuthorization");
        jsonObject.put("errorMsg", "Token is Null or Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    /**
     * 从请求头取出用户token
     *
     * @param exchange
     */
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("bearer", "");
    }

    /**
     * 判断该接口是否需要token
     *
     * @param exchange
     */
    private boolean isRequire(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (noRequireTokenUris.contains(path)) {
            // 不需要token
            return false;
        }
        return Boolean.TRUE;
    }

    /**
     * 拦截的顺序
     */
    @Override
    public int getOrder() {
        return 0;
    }
}