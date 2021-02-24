package org.jnyou.nettyserver.base;

import org.jnyou.nettyserver.enums.MsgType;

public class PingMsg extends BaseMsg {
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}