package org.jnyou.common.exception;

/**
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5位数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：10001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002: 短信验证码请求频率太高
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *  15: 用户
 *  21: 库存
 *
 *
 * @author jnyou
 */
public enum BizCodeEnume {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    TO_MANY_REQUEST(10002,"请求流量过大"),
    SMS_CODE_EXCEPTION(10002,"验证码获取频率太高，稍后再试"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常"),
    USER_EXIST_EXCEPTION(15001,"用户已存在"),
    PHONE_EXIST_EXCEPTION(15002,"手机号已存在"),
    LOGINACCT_PASSWORD_INVAILD_EXCEPTION(15003,"用户名或密码错误"),
    NO_STOCK_EXCEPTION(21000,"商品库存不足")
    ;

    private final int code;
    private String msg;
    BizCodeEnume(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
