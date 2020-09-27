package com.blithe.cms.common.exception;

/**
 * @Author: nankexiansheng
 * @Description: 自定义异常
 * @Date: 2020/3/10
 * @Param:
 * @Return:
 **/
public class RbacException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RbacException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RbacException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RbacException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RbacException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
