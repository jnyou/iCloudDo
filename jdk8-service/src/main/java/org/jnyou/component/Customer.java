package org.jnyou.component;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * 给出客户ID列表查询客户列表。
 * 基于存在的客户列表中的客户ID查询订单列表。
 * 基于订单列表转换为订单DTO视图列表。
 */

@Data
class Customer {

    private Long id;


    @Data
    static class Order {

        private Long id;
        private String orderId;
        private Long customerId;
    }

    @Data
    static class OrderDto {
        private String orderId;
    }

    // 模拟客户查询
    private static List<Customer> selectCustomers(List<Long> ids) {
        return null;
    }

    // 模拟订单查询
    private static List<Order> selectOrders(List<Long> customerIds) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        List<Long> ids = new ArrayList<>();
        List<OrderDto> view = Optional.ofNullable(selectCustomers(ids))
                .filter(cs -> !cs.isEmpty())
                .map(cs -> selectOrders(cs.stream().map(Customer::getId).collect(Collectors.toList())))
                .map(orders -> {
                    List<OrderDto> dtoList = new ArrayList<>();
                    orders.forEach(o -> {
                        OrderDto dto = new OrderDto();
                        dto.setOrderId(o.getOrderId());
                        dtoList.add(dto);
                    });
                    return dtoList;
                }).orElse(Collections.emptyList());
    }
}