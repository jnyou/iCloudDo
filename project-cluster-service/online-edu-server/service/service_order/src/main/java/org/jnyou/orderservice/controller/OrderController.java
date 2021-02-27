package org.jnyou.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.commonutils.JwtUtils;
import org.jnyou.commonutils.R;
import org.jnyou.orderservice.entity.Order;
import org.jnyou.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author jnyou
 * @since 2020-08-09
 */
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 生成订单的方法
     * @param courseId
     * @param request
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        // 创建订单。返回订单号
        String orderNo = orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().put("orderNo",orderNo);
    }


    /**
     * 根据订单号查询订单
     * @param orderNo
     * @return
     * @Author jnyou
     * @Date 2020/8/15
     */
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfoByOrderNo(@PathVariable String orderNo){
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        return R.ok().put("item",order);
    }

    /**
     * 根据用户id和课程id查询支付状态（判断是否购买课程）
     * @param courseId
     * @param memberId
     * @return
     * @Author jnyou
     * @Date 2020/8/22
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        // 1 : 代表已经支付
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        return count > 0 ? true : false;
    }

}

