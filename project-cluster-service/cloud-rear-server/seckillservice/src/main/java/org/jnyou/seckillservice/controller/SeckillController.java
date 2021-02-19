package org.jnyou.seckillservice.controller;

import org.jnyou.common.utils.R;
import org.jnyou.seckillservice.service.SeckillService;
import org.jnyou.seckillservice.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillController
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@RestController
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    /**
     * 返回当前时间可以参与的秒杀商品信息
     */
    @GetMapping("/getCurrentSeckillSkus")
    public R getCurrentSeckillSkus(){
        List<SeckillSkuRedisTo> seckillSkuRedisTos = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(seckillSkuRedisTos);
    }

    /**
     * 查询某个商品是否参与秒杀
     */
    @GetMapping("/sku/seckill/{skuId}")
    public R getCurrentSeckillInfo(@PathVariable("skuId") Long skuId){
        SeckillSkuRedisTo to = seckillService.getCurrentSeckillInfo(skuId);
        return R.ok().setData(to);
    }

    // http://seckill.gmall.com/kill?killId=4_10&key=undefined&num=1
    @GetMapping("/kill")
    public R seckill(@RequestParam("killId") String killId, @RequestParam("key") String key,@RequestParam("num") Integer num){
        String orderSn = seckillService.kill(killId,key,num);
        return R.ok().setData(orderSn);
    }



}