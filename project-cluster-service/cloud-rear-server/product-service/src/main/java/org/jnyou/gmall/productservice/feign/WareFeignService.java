package org.jnyou.gmall.productservice.feign;

import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *
 * @ClassName WareFeignService
 * @Author: JnYou
 **/
@FeignClient("ware-service")
public interface WareFeignService {

    /**
     * 1、R 在设计的时候加上泛型
     * 2、直接返回我们想要的结果
     * 3、自己封装解析结果
     */

    /**
     * 查询sku是否有库存
     * @param skuIds
     * @return R<List<SkuHasStockVo>>
     */
    @PostMapping("/ware/waresku/hasStock")
    public R getSkuHasStock(@RequestBody List<Long> skuIds);

}