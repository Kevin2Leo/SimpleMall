package com.cskaoyan.mall.model.vo.messageVO;


import com.cskaoyan.mall.model.bean.Goods;

import java.util.Date;

public class RepliedMsgVO {

    private Integer id;//留言id
    private Integer userId;
    private Integer goodsId;
    private String content;
    private String replyContent;
    private Integer state;//1表示未回复 ，0表示已经回复
    private Date createtime;
    private Goods goods;
    private Userrr user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Userrr getUser() {
        return user;
    }

    public void setUser(Userrr user) {
        this.user = user;
    }
}
