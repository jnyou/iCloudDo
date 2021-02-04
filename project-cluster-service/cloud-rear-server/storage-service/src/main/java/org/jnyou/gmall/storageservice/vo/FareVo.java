package org.jnyou.gmall.storageservice.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * FareVo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class FareVo {

    private MemberAddressVo address;
    private BigDecimal fare;

}