package com.cskaoyan.mall.model.vo.goodsVO;

import com.cskaoyan.mall.model.bean.Goods;
import com.cskaoyan.mall.model.bean.Spec;

import java.util.List;

/**
 * 展示商品详情的VO
 */
public class GoodsInfoVO {

    private List<Spec> specs;

    private GoodsByTypeVO goods;

    private String img;
    private String name;
    private String desc;
    private Integer typeId;
    private Double unitPrice;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public GoodsByTypeVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsByTypeVO goods) {

        this.goods = goods;
    }
}
