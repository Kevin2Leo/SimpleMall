package com.cskaoyan.mall.model.bean;

/**
 * 规格对应的java类
 */
public class Spec {

    private Integer id;//规格id
    private String specName;//规格名称
    private Integer stockNum;//该规格下的库存
    private Double unitPrice;//该规格产品的单价
    private Integer goodsId;//该规格对应的商品id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
}
