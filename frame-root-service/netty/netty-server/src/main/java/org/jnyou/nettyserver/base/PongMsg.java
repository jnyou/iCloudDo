package org.jnyou.nettyserver.base;

import org.jnyou.nettyserver.enums.MsgType;

public class PongMsg extends BaseMsg {
    public PongMsg() {
        setType(MsgType.PONG);
    }
}