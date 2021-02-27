package org.jnyou.orderservice.service.impl;

import org.jnyou.commonutils.entity.CourseDetailsVo;
import org.jnyou.commonutils.entity.UcenterMember;
import org.jnyou.commonutils.utils.RandomNoUtil;
import org.jnyou.orderservice.client.CourseClient;
import org.jnyou.orderservice.client.UcenterClient;
import org.jnyou.orderservice.entity.Order;
import org.jnyou.orderservice.mapper.OrderMapper;
import org.jnyou.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-08-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberId) {

        // 通过远程调用通过用户id获取用户信息
        UcenterMember ucenterMember = ucenterClient.getInfo(memberId);
        // 通过远程调用通过课程id获取课程信息
        CourseDetailsVo courseDto = courseClient.getCourseInfoDto(courseId);

        Order order = new Order();
        // 订单号
        order.setOrderNo(RandomNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        // 订单状态 0：未支付  1：已支付
        order.setStatus(0);
        // 支付类型，1：微信
        order.setPayType(1);
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();
    }
}
