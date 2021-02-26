package io.jnyou.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p> 网关sentinel
 * SentinelConfig
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
public class SentinelConfig {

    public SentinelConfig() {
        GatewayCallbackManager.setBlockHandler(new com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler() {
            // 网关限流了请求，就会调用此回调
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
//                R r = R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(), BizCodeEnume.TO_MANY_REQUEST.getMsg());
//                Mono<ServerResponse> body = ServerResponse.ok().body(Mono.just(JSON.toJSONString(r)), String.class);
                Map<String ,Object> map = new HashMap<>(124);
                map.put("449","访问流量过大");
                return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(map));
            }
        });
    }


}