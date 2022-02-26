package com.cskaoyan.mall.model.vo.goodsVO;


import com.cskaoyan.mall.model.bean.Reply;

import java.util.Date;

public class GoodsMsgVO {

    private Integer id;//问答 id
    private String content;//留言内容
    private String asker;//nickname
    private Date time;//留言时间
    private Reply reply;//回复内容

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

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

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
