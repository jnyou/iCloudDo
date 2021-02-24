package org.jnyou.nettyserver.base;

import org.jnyou.nettyserver.enums.MsgType;

public class PushMsg extends BaseMsg {
    private String title;
    private String content;


    public PushMsg() {
        setType(MsgType.PUSH);
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


}