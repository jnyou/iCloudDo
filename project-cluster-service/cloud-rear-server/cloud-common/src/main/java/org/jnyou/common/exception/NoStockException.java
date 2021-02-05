package org.jnyou.common.exception;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * NoStockException
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class NoStockException extends RuntimeException{
    private Long skuId;
    private String msg;
    public NoStockException(Long skuId) {
        super("商品id"+skuId+"没有足够的库存了.");
    }
    public NoStockException(String msg) {
        super(msg);
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}