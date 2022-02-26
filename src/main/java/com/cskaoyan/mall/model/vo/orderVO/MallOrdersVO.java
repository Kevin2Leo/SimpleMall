package com.cskaoyan.mall.model.vo.orderVO;



import com.cskaoyan.mall.model.vo.goodsVO.GoodsMallVO;

import java.util.Date;

public class MallOrdersVO {

    private Integer id;//订单id 主键
    private Integer state;//订单状态
    private Integer goodsNum;//购买商品的数量
    private Double amount;//总金额
    private Integer goodsDetailId;//商品规格的id
    private Date createtime;//订单创建时间
    private Boolean hasComment;//是否评价
    private GoodsMallVO goods;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getHasComment() {
        return hasComment;
    }

    public void setHasComment(Boolean hasComment) {
        this.hasComment = hasComment;
    }

    public GoodsMallVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsMallVO goods) {
        this.goods = goods;
    }
}
