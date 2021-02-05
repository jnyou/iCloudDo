package org.jnyou.gmall.storageservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class WareSkuLockVo {
    private String OrderSn;

    // 需要锁定的所有库存信息
    private List<OrderItemVo> locks;
}
