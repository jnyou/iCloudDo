package org.jnyou.gmall.productservice.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.jnyou.common.exception.BizCodeEnume;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.productservice.feign.SeckillFeignClient;
import org.springframework.stereotype.Component;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillFeignFallbackImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Slf4j
@Component
public class SeckillFeignFallbackImpl implements SeckillFeignClient {
    @Override
    public R getCurrentSeckillInfo(Long skuId) {
        log.info("熔断保护开启...");
        return R.error(BizCodeEnume.NO_STOCK_EXCEPTION.getCode(), BizCodeEnume.NO_STOCK_EXCEPTION.getMsg());
    }
}