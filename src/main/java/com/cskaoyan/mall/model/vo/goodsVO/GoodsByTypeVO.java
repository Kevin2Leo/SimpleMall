package com.cskaoyan.mall.model.vo.goodsVO;

/**
 * "id": 499,
 * "img": "http://115.29.141.32:8084/static/image/1571233231472156699966867820190828214043.jpg",
 * "name": "ipad",
 * "price": 1000.0,
 * "typeId": 194,
 * "stockNum": 999
 */
public class GoodsByTypeVO {

    private Integer id;
    private String img;
    private String name;
    private Double price;
    private Integer typeId;
    private Integer stockNum;
    private String desc;

    private Double unitPrice;

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {

        if (img.startsWith("http://localhost:8084/")){
            this.img = img;
        }
        else if (img.startsWith("http://localhost:8085/")){
            img.replace("http://localhost:8085/","http://localhost:8084/");
            this.img = img;
        }
        else {
            this.img = "http://localhost:8084/" + img ;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

}
