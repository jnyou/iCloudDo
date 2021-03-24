package io.jnyou.disruptor;

import com.lmax.disruptor.EventHandler;
import io.jnyou.model.Order;
import io.jnyou.model.OrderBooks;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * OrderEventHandler
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent> {

    private OrderBooks orderBooks;

    private String symbol ;

    public OrderEventHandler(OrderBooks orderBooks) {
        this.orderBooks = orderBooks;
        this.symbol =  this.orderBooks.getSymbol() ;
    }


    /**
     * 接收到了某个消息
     *
     * @param event
     * @param sequence
     * @param endOfBatch
     * @throws Exception
     */
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        // 从ringbuffer 里面接收了某个数据
        Order order = (Order)event.getSource();
        if(!order.getSymbol().equals(symbol)){ // 我们接收到了一个不属于我们处理的数据,我们不处理
            return;
        }

//        log.info("开始接收订单事件============>{}", event);

//        MatchServiceFactory.getMatchService(MatchStrategy.LIMIT_PRICE).match(orderBooks ,order);

        /// 处理逻辑是啥?
//        log.info("处理完成我们的订单事件===================>{}", event);
    }
}