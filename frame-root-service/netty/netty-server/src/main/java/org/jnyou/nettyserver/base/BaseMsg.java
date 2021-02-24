package org.jnyou.nettyserver.base;

import org.jnyou.nettyserver.enums.MsgType;

import java.io.Serializable;

public class BaseMsg implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;//唯一标示防止channel调用混乱
    private MsgType type;

    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }


    public String getClientId() {
        return clientId;
    }


    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public MsgType getType() {
        return type;
    }


    public void setType(MsgType type) {
        this.type = type;
    }

}