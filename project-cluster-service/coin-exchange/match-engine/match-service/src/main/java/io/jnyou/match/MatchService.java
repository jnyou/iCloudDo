package io.jnyou.match;

import io.jnyou.model.Order;
import io.jnyou.model.OrderBooks;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MatchService 撮合/交易的接口定义
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public interface MatchService {

    /**
     * 进行订单的撮合交易
     *
     * @param order
     */
    void match(OrderBooks orderBooks, Order order);

}
