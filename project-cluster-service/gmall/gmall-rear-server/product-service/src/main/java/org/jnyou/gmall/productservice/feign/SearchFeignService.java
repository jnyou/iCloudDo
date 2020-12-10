package org.jnyou.gmall.productservice.feign;

import org.jnyou.common.to.es.SkuEsModel;
import org.jnyou.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author jnyou
 */
@FeignClient("search-service")
public interface SearchFeignService {

    @PostMapping("/search/product/save")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
