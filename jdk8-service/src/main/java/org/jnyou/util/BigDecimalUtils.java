package org.jnyou.util;

import java.math.BigDecimal;

/**
 * 分类名称
 *
 * @ClassName BigDecimalUtils
 * @Description: bigdecimal工具类，重写求和方法
 * @Author: jnyou
 **/
public class BigDecimalUtils {

    public static BigDecimal ifNullSet0(BigDecimal in) {
        if (in != null) {
            return in;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal sum(BigDecimal... in) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < in.length; i++) {
            result = result.add(ifNullSet0(in[i]));
        }
        return result;
    }
}