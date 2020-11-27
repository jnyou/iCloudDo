package com.blithe.cms.common.tools.redis;

import java.io.Serializable;

/**
 * Description 消息类(实现Serializable接口)
 * @author jnyou
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 559867242872277374L;

    private int id;

    private String content;

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
