package com.cskaoyan.mall.model.bo.orderBO;

/**
 * {
 * "token":"dzzz",
 * "goodsDetailId":1474,
 * "num":7,
 * "state":1,
 * "amount":0.7000000000000001
 * }
 */
public class AddOrderBO {

    private String token;//用户昵称
    private Integer goodsDetailId;//规格id
    private Integer num;//购买数量
    private Integer state;//订单状态，未付款是0,付了款是1
    private Double amount;//总金额

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
