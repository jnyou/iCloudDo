package org.jnyou.common.to.mq;

import lombok.Data;

@Data
public class StockLockedTo {
    private Long id; // 库存工作单的id
    private StockDetailTo detailTo; // 库存工作单的详情
}
