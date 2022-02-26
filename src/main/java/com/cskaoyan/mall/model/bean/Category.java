package com.cskaoyan.mall.model.bean;

/**
 * 商品分类
 */
public class Category {

    private Integer id;//商品类别id
    private String name;//商品类别名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
