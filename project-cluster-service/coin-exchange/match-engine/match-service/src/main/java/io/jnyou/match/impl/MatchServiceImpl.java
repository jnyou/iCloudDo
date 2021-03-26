package io.jnyou.match.impl;

import io.jnyou.match.MatchService;
import io.jnyou.model.Order;
import io.jnyou.model.OrderBooks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MatchServiceImpl
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Override
    public void match(OrderBooks orderBooks, Order order) {
        log.info("撮合引擎开始启动...");
    }
}