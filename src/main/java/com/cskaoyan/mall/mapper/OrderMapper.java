package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.model.bean.*;
import com.cskaoyan.mall.model.bo.orderBO.ChangeOrderBO;
import com.cskaoyan.mall.model.bo.orderBO.OrdersByPagesBO;
import com.cskaoyan.mall.model.vo.orderVO.OrdersVO;
import com.cskaoyan.mall.model.vo.orderVO.ShowVO;

import java.util.List;

public interface OrderMapper {

    Integer getTotalCount(OrdersByPagesBO ordersByPagesBO);//用来获得total的

    List<OrdersVO> pageOrders(OrdersByPagesBO ordersByPagesBO);//

    void deleteOrder(int id);//根据订单id 删除订单

    ShowVO order(int id);

    List<Spec> getSpecInfo(int goodsId);

    void changeOrder(ChangeOrderBO changeOrderBO);

    List<Orders> getOrderByState(Orders orders);//按订单状态 和 用户昵称 获得订单

    User getUser(String nickname);//根据nickname查找User的所有信息

    Spec getSpec(Integer specId);//根据specId 获得所有规格的的信息

    Goods getGoods(Integer goodsId);//根据goodsId 获得所有商品信息

    void addOrder(Orders orders);//把orders 装进数据库中

    void updateState(Integer id);//根据订单id 改变 订单状态state

    void confirmReceive(int id);//确认收货

    void updateHasComment(Integer orderId);//根据订单id 改变hasComment的值


    void sendComment(Comment comment);//添加评论

}
