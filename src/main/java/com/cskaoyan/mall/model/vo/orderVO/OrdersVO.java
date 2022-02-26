package com.cskaoyan.mall.model.vo.orderVO;



public class OrdersVO {

    private Integer id;//订单id

    private Integer userId;//用户id

    private Integer goodsDetailId;

    private String goods;

    private String spec;//规格名

    private Integer goodsNum;//购买商品的数量

    private Double amount;//金额

    //订单状态 -1表示全部,0表示未付款,1表示未发货,2感觉已发货,3已到货
    private Integer stateId;

    private String state;//订单状态(字符串描述)

    private UserVO user = new UserVO();

    private String nickname;//用户昵称

    private String name;//用户名

    private String address;//收货地址

    private String phone;//电话
//========================================================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
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


    public Integer getStateId() {

        return stateId;
    }

    //vo里面的stateId赋值成功其实是利用该set方法完成赋值的
    public void setStateId(Integer stateId) {
        if(stateId == 0){
            setState("未付款");
        }
        else if(stateId == 1){
            setState("未发货");
        }
        else if (stateId == 2){
            setState("已发货");
        }
        else if (stateId ==3){
            setState("已到货");
        }
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }


    public void setNickname(String nickname) {
//        this.nickname = nickname;
        user.setNickname(nickname);
    }

    public void setName(String name) {
//        this.name = name;
        user.setName(name);
    }


    public void setAddress(String address) {
//        this.address = address;
        user.setAddress(address);
    }


    public void setPhone(String phone) {
//        this.phone = phone;
        user.setPhone(phone);
    }
}
