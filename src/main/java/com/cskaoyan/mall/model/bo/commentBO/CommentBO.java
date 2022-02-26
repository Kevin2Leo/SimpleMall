package com.cskaoyan.mall.model.bo.commentBO;

/**
 *  "token": "dzzz",
 * 	"orderId": 3529,
 * 	"goodsId": 1015,
 * 	"goodsDetailId": 2577,
 * 	"content": "气味儿啊",
 * 	"score": 100
 */
public class CommentBO {

    private String token;//用户昵称
    private Integer orderId;
    private Integer goodsId;//商品id
    private Integer goodsDetailId;
    private String content;//评价的具体内容
    private Double score;//评价分数

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
