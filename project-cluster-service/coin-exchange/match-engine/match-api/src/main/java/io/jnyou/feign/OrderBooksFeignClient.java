package io.jnyou.feign;

import io.jnyou.config.feign.FeignConfig;
import io.jnyou.domain.DepthItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "match-service", contextId = "orderBooksFeignClient", configuration = FeignConfig.class)
public interface OrderBooksFeignClient {


    /**
     * 查询该交易对的盘口数据
     * key :sell:asks   value: List<DepthItemVo>
     * key:buy:bids    value:List<DepthItemVo>
     * @param symbol
     * @return
     */
    @GetMapping("/match/depth")
    Map<String, List<DepthItemVo>> querySymbolDepth(@RequestParam String symbol) ;
}
