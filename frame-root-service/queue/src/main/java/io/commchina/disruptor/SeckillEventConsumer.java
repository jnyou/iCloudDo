package io.commchina.disruptor;

import com.lmax.disruptor.EventHandler;
import io.commchina.common.config.SpringUtil;
import io.commchina.common.entity.Result;
import io.commchina.common.enums.SeckillStatEnum;
import io.commchina.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者(秒杀处理器)
 */
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {

	private final static Logger LOGGER = LoggerFactory.getLogger(SeckillEventConsumer.class);
	
	private ISeckillService seckillService = (ISeckillService) SpringUtil.getBean("seckillService");
	
	@Override
    public void onEvent(SeckillEvent seckillEvent, long seq, boolean bool) {
		Result result =
				seckillService.startSeckilAopLock(seckillEvent.getSeckillId(), seckillEvent.getUserId());
		if(result.equals(Result.ok(SeckillStatEnum.SUCCESS))){
			LOGGER.info("用户:{}{}",seckillEvent.getUserId(),"秒杀成功");
		}
	}
}
