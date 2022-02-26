package com.cskaoyan.mall.model.bo.orderBO;

/**
 * 购物车下单
 * "cartList": [{
 * 		"id": 5,
 * 		"goodsNum": 5,
 * 		"amount": 1110
 *        },
 */
public class CartBO {

    private Integer id;//订单id
    private Integer goodsNum;//购买商品的数量
    private Double amount;//总金额

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
