package io.jnyou.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.openhft.affinity.AffinityThreadFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * DisruptorAutoConfiguration
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Configuration
@EnableConfigurationProperties(value = DisruptorProperties.class)
public class DisruptorAutoConfiguration {

    private DisruptorProperties disruptorProperties;

    public DisruptorAutoConfiguration(DisruptorProperties disruptorProperties) {
        this.disruptorProperties = disruptorProperties;
    }

    @Bean
    public EventFactory<OrderEvent> eventEventFactory() {
        EventFactory<OrderEvent> orderEventEventFactory = new EventFactory<OrderEvent>() {
            @Override
            public OrderEvent newInstance() {
                return new OrderEvent();
            }
        };
        return orderEventEventFactory;
    }

    @Bean
    public ThreadFactory threadFactory() {
        return new AffinityThreadFactory("Match-Handler:");
    }

    /**
     * 无锁高效的等待策略
     *
     * @return
     */
    @Bean
    public WaitStrategy waitStrategy() {
        return new YieldingWaitStrategy();
    }

    /**
     * 创建一个RingBuffer
     * eventFactory: 事件工厂
     * threadFactory:   我们执行者(消费者)的线程该怎么创建
     * waitStrategy : 等待策略: 当我们ringBuffer 没有数据时,我们怎么等待
     */
    @Bean
    public RingBuffer<OrderEvent> ringBuffer(
            EventFactory<OrderEvent> eventFactory,
            ThreadFactory threadFactory,
            WaitStrategy waitStrategy,
            EventHandler<OrderEvent>[] eventHandlers) {

        Disruptor<OrderEvent> disruptor = null;

        ProducerType producerType = ProducerType.SINGLE;

        // 生产者是否多个，设置相关ProducerType
        if (disruptorProperties.isMultiProducer()) {
            producerType = ProducerType.MULTI;
        }

        /**
         *
         * EventFactory<T> eventFactory,
         * int ringBufferSize,
         * ThreadFactory threadFactory,
         * ProducerType producerType,
         * WaitStrategy waitStrategy
         */
        disruptor = new Disruptor<OrderEvent>(eventFactory,disruptorProperties.getRingBufferSize(),threadFactory,producerType,waitStrategy);
        disruptor.setDefaultExceptionHandler(new DisruptorHandlerException());


        // 设置消费者---我们的每个消费者代表我们的一个交易对；有多少个交易对,我们就有多少个eventHandlers；事件来了后,多个eventHandlers 是并发执行的
        disruptor.handleEventsWith(eventHandlers);

        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        // 开始监听
        disruptor.start();

        final Disruptor<OrderEvent> disruptorShutdown = disruptor;

        // 使用优雅的停机
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    disruptorShutdown.shutdown();
                }, "DisruptorShutdownThread"
        ));
        return ringBuffer;
    }


    /**
     * 创建DisruptorTemplate
     *
     * @param ringBuffer
     * @return
     */
    @Bean
    public DisruptorTemplate disruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        return new DisruptorTemplate(ringBuffer);
    }

}