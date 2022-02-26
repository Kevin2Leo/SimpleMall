package com.cskaoyan.mall.model.vo.orderVO;

import java.util.List;


public class OrdersByPagesVO {

    private Integer total;

    private List<OrdersVO> orders;




    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<OrdersVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersVO> orders) {
        this.orders = orders;
    }
}
