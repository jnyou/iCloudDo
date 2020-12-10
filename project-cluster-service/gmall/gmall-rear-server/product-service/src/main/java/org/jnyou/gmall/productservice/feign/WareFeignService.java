package org.jnyou.gmall.productservice.feign;

import com.alibaba.fastjson.TypeReference;
import org.jnyou.common.to.SkuHasStockVo;
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
     * 查询sku是否有库存
     */
    @PostMapping("/ware/waresku/hasStock")
    public R<List<SkuHasStockVo>> getSkuHasStock(@RequestBody List<Long> skuIds);

}