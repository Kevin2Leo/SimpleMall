package com.cskaoyan.mall.model.bo.orderBO;


public class OrdersByPagesBO {

    private Integer id;//主键 订单id

    //订单状态 -1表示全部,0表示未付款,1表示未发货,2感觉已发货,3已到货
    private Integer state;
    //当前页 可选值从1开始
    private Integer currentPage;
    //指的是每页显示的订单数
    private Integer pagesize ;
    //金额上限
    private Integer moneyLimit1 ;
    //金额下限
    private Integer moneyLimit2 ;

    private String goods;//商品的名称
    private String address;//收货地址
    private String name;//用户昵称



//=======================================================================

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getMoneyLimit1() {
        return moneyLimit1;
    }

    public void setMoneyLimit1(Integer moneyLimit1) {
        this.moneyLimit1 = moneyLimit1;
    }

    public Integer getMoneyLimit2() {
        return moneyLimit2;
    }

    public void setMoneyLimit2(Integer moneyLimit2) {
        this.moneyLimit2 = moneyLimit2;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
