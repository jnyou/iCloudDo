package io.jnyou.feign;

import io.jnyou.config.feign.FeignConfig;
import io.jnyou.dto.CoinDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CoinServiceFeign
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient(value = "finance-service",configuration = FeignConfig.class)
public interface CoinServiceFeign {

    List<CoinDto> findCoins(List<Long> coinIds);

}