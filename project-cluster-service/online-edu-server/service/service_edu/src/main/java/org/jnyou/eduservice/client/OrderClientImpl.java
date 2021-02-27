package org.jnyou.eduservice.client;

import com.alibaba.excel.event.Order;
import org.springframework.stereotype.Component;

/**
 * 分类名称
 *
 * @ClassName OrderClientImpl
 * @Description: 熔断器机制
 * @Author: jnyou
 * @create 2020/08/22
 * @module 智慧园区
 **/
@Component
public class OrderClientImpl implements OrderClient {

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}