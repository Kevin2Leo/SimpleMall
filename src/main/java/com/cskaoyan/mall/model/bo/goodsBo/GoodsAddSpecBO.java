package com.cskaoyan.mall.model.bo.goodsBo;

/**
 * 商品规格
 * 例如：64G  128G  256G
 * {"specName":"内存256G","stockNum":"564","unitPrice":"72.99"}
 */
public class GoodsAddSpecBO {

    private String specName;//规格名称
    private Integer stockNum;//该规格的库存数量
    private Double unitPrice;//该规格的价格

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
}
