package org.jnyou.mall.cart.vo;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * CartVo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class CartVo {

    /**
     * 购物车子项信息
     */
    List<CartItemVo> items;

    /**
     * 商品数量(总共多少件商品)
     */
    private Integer countNum;

    /**
     * 商品类型数量（商品种类）
     */
    private Integer countType;

    /**
     * 商品总价
     */
    private BigDecimal totalAmount;

    /**
     * 减免价格
     */
    private BigDecimal reduce = new BigDecimal("0.00");

    public List<CartItemVo> getItems() {
        return items;
    }

    public void setItems(List<CartItemVo> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count=0;
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVo item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        int count=0;
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVo item : items) {
                count += 1;
            }
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVo item : items) {
                amount.add(item.getTotalPrice());
            }
        }
        // 减去优惠总价
        amount.subtract(reduce);
        return amount;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }

}