package com.cskaoyan.mall.model.vo.goodsVO;


public class GoodsMallVO {

    private Integer id;//商品id
    private String img;//商品图片
    private String name;//商品名称
    private Integer goodsDetailId;//商品规格id
    private String spec;//商品规格名
    private Double unitPrice;//该规格的价钱

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

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
