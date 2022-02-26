package com.cskaoyan.mall.model.bo.messageBO;


import org.omg.PortableInterceptor.INACTIVE;

public class ReplyBO {

    private Integer id;//订单id
    private String content;//reply的内容 回复的内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
