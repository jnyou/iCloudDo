package org.jnyou.service.templete;

import org.jnyou.entity.Goods;

/**
 * @ClassName GoodsService
 * @Description:
 * @Author: jnyou
 **/
public interface GoodsService {

    Goods getGoodsById(Integer id);


    Goods getGoodsByIdTemplate(Integer id);
}