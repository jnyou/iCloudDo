package org.jnyou.gmall.storageservice.listener;

import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import org.jnyou.common.to.mq.OrderTo;
import org.jnyou.common.to.mq.StockDetailTo;
import org.jnyou.common.to.mq.StockLockedTo;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.storageservice.entity.WareOrderTaskDetailEntity;
import org.jnyou.gmall.storageservice.entity.WareOrderTaskEntity;
import org.jnyou.gmall.storageservice.service.WareSkuService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * StockReleaseListener
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
@RabbitListener(queues = "stock.release.stock.queue")
public class StockReleaseListener {

    @Autowired
    WareSkuService wareSkuService;

    /**
     * 库存自动解锁
     * @param stock
     * @param message
     * @Author JnYou
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo stock, Message message, Channel channel) throws IOException {
        try {
            System.out.println("收到解锁库存的消息");
            wareSkuService.unLockStock(stock);
            // 手动ack机制
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            // 消息被拒绝之后重新放入队列，让其他的消费者继续消费。
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }

}