package org.jnyou.gmall.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;
import org.jnyou.gmall.orderservice.dao.OrderItemDao;
import org.jnyou.gmall.orderservice.entity.OrderEntity;
import org.jnyou.gmall.orderservice.entity.OrderItemEntity;
import org.jnyou.gmall.orderservice.entity.OrderReturnReasonEntity;
import org.jnyou.gmall.orderservice.service.OrderItemService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = {"java-queue"})
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 接受消息的类型：class org.springframework.amqp.core.Message
     * Message：原生消息详细信息。头 + 体
     * T<发送的消息类型> 比如 OrderReturnReasonEntity content 。会自动转换 ，不需要手动转换
     * channel：当前传输数据的通道
     *
     * @param msg
     * @RabbitListener : 标在类上，监听哪些队列即可
     * @RabbitHandler 标在方法上（重载区分不同类型的消息）
     * @Author JnYou
     */
//    @RabbitListener(queues = {"java-queue"})
    @RabbitHandler
    public void recieveMessage(Message msg, OrderReturnReasonEntity content, Channel channel) {
        // 接受OrderReturnReasonEntity的消息
        // 消息体信息， {"id":1,"name":"退货","sort":null,"status":null,"createTime":1612086973704}
        byte[] body = msg.getBody();
        // 消息头信息 MessageProperties [headers={__TypeId__=org.jnyou.gmall.orderservice.entity.OrderReturnReasonEntity}, contentType=application/json, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=java-exchange, receivedRoutingKey=hello.java, deliveryTag=1, consumerTag=amq.ctag-0A2cdfMWgZtvnpuxYPOotw, consumerQueue=java-queue]
        MessageProperties messageProperties = msg.getMessageProperties();
        System.out.println("接受到OrderReturnReasonEntity类型的消息内容：" + msg + "====》内容" + content);
        // 消费者确认逻辑。。。
        // deliveryTag在channel内按顺序自增的。
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        try {
            if(deliveryTag % 2 ==0){
                // false 非批量模式，一个一个签收 相当于收货
                channel.basicAck(deliveryTag, false);
                System.out.println("签收了货物。。。" + deliveryTag);
            } else {
                // 退货
                // long deliveryTag, boolean multiple, boolean requeue：是否重新放入队列，false：丢弃，true：发回服务器，重新入队。
                channel.basicNack(deliveryTag,false,false);
                // long deliveryTag, boolean requeue
//                channel.basicReject();
                System.out.println("没有签收货物");
            }
        } catch (IOException e) {
            // 网络中断
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void recieveMessage2(OrderEntity content) {
        // 接受OrderEntity的消息
        System.out.println("接受到OrderEntity类型的消息内容：" + content);
    }

}