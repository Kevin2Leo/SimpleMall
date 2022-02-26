package com.cskaoyan.mall.service;

import com.cskaoyan.mall.model.bo.commentBO.CommentBO;
import com.cskaoyan.mall.model.bo.orderBO.*;
import com.cskaoyan.mall.model.vo.orderVO.MallOrdersVO;
import com.cskaoyan.mall.model.vo.orderVO.OrdersByPagesVO;
import com.cskaoyan.mall.model.vo.orderVO.ShowVO;

import java.util.List;

public interface OrderService {

    //根据请求 获得订单页面
    OrdersByPagesVO orderByPage(OrdersByPagesBO ordersByPagesBO);

    //删除订单
    void deleteOrder(int id);

    ShowVO order(int id);

    void changeOrder(ChangeOrderBO changeOrderBO);

    //getOrderByStateBO 里面装的是state(订单状态) 和 token(昵称)
    List<MallOrdersVO> getOrderByState(GetOrderByStateBO getOrderByStateBO);

    void addOrder(AddOrderBO addOrderBO);//添加购物车 或 直接付款

    void settleAccounts(CartListBO cartListBO);//清空购物车

    void pay(int id);//确认付款

    void confirmReceive(int id);//确认收货

    void sendComment(CommentBO commentBO);//发表评价
}
