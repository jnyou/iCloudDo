package org.jnyou.gmall.orderservice.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * WareFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient("ware-service")
public interface WareFeignClient {

    @PostMapping("/ware/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);

    @GetMapping("/ware/wareinfo/fare/{addrId}")
    R getFare(@PathVariable("addrId") Long addrId);

}
