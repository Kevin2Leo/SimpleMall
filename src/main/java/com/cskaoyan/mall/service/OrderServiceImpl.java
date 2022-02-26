package com.cskaoyan.mall.service;

import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.model.bean.*;
import com.cskaoyan.mall.model.bo.commentBO.CommentBO;
import com.cskaoyan.mall.model.bo.orderBO.*;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsMallVO;
import com.cskaoyan.mall.model.vo.orderVO.*;
import com.cskaoyan.mall.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Override
    public OrdersByPagesVO orderByPage(OrdersByPagesBO ordersByPagesBO) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        // VO中的很多数据 都容易拿，但要考虑的是total怎么拿
        // total:会根据 查询条件、订单的状态 进行改变
        // orders: 当前页展示出来的订单信息 和 查询条件、订单状态、当前页码 有关
        Integer count = orderMapper.getTotalCount(ordersByPagesBO);//得到 total

        List<OrdersVO> ordersList = orderMapper.pageOrders(ordersByPagesBO);

        OrdersByPagesVO ordersByPagesVO = new OrdersByPagesVO();
        ordersByPagesVO.setOrders(ordersList);
        ordersByPagesVO.setTotal(count);

        session.commit();
        session.close();
        return ordersByPagesVO;

    }

    @Override
    public void deleteOrder(int id) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        orderMapper.deleteOrder(id);
        session.commit();
        session.close();
    }

    @Override
    public ShowVO order(int id) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        ShowVO showVO = orderMapper.order(id);

        Integer goodsId = showVO.getGoodsId();
        List<Spec> specs = orderMapper.getSpecInfo(goodsId);
        showVO.setSpec(specs);

        List<States> statesList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            States states = new States();
            states.setId(i);
            statesList.add(states);
        }
        showVO.setStates(statesList);

        CurState curState = new CurState();
        curState.setId(showVO.getState());
        CurSpec curSpec = new CurSpec();
        curSpec.setId(showVO.getGoodsDetailId());

        showVO.setCurState(curState);
        showVO.setCurSpec(curSpec);

        showVO.setGoodsId(null);
        session.commit();
        session.close();
        return showVO;
    }

    @Override
    public void changeOrder(ChangeOrderBO changeOrderBO) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        orderMapper.changeOrder(changeOrderBO);

        session.commit();
        session.close();
    }

    @Override
    public List<MallOrdersVO> getOrderByState(GetOrderByStateBO getOrderByStateBO) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        //将GetOrderByStateBO 转换为 Orders
        Orders orders = new Orders();
        orders.setNickname(getOrderByStateBO.getToken());
        orders.setStatus(getOrderByStateBO.getState());

        List<Orders> ordersList = orderMapper.getOrderByState(orders);
        //将List<Orders> 转换为 List<MallOrdersVO>
        List<MallOrdersVO> mallOrdersVOList = new ArrayList<MallOrdersVO>();

        for (Orders orders1 : ordersList) {
            MallOrdersVO mallOrdersVO = new MallOrdersVO();

            mallOrdersVO.setId(orders1.getId());
            mallOrdersVO.setState(orders1.getStatus());
            mallOrdersVO.setGoodsNum(orders1.getNum());
            mallOrdersVO.setAmount(orders1.getAmount());
            mallOrdersVO.setGoodsDetailId(orders1.getSpecId());
            mallOrdersVO.setCreatetime(orders1.getCreatetime());
            if (orders1.getHasComment() == 0) {//0表示未评价
                mallOrdersVO.setHasComment(false);
            } else if (orders1.getHasComment() == 1) {//1表示已评价
                mallOrdersVO.setHasComment(true);
            }
            GoodsMallVO goods = new GoodsMallVO();
            goods.setId(orders1.getGoodsId());//商品id
            goods.setImg(orders1.getImage());//商品图片
            goods.setName(orders1.getGoodsName());//商品名
            goods.setGoodsDetailId(orders1.getSpecId());//规格id
            goods.setSpec(orders1.getSpecName());//规格名
            goods.setUnitPrice(orders1.getPrice());//该规格下的价钱
            //把GoodsMallVO 装到 MallOrdersVO里面去
            mallOrdersVO.setGoods(goods);
            mallOrdersVOList.add(mallOrdersVO);
        }

        return mallOrdersVOList;
    }

    @Override
    public void addOrder(AddOrderBO addOrderBO) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        //将AddOrderBO 转换为 Orders
        Orders orders = new Orders();
        orders.setNickname(addOrderBO.getToken());//用户昵称
        orders.setSpecId(addOrderBO.getGoodsDetailId());//规格id
        orders.setNum(addOrderBO.getNum());//购买数量
        orders.setStatus(addOrderBO.getState());//订单状态
        orders.setAmount(addOrderBO.getAmount());//总金额
        orders.setHasComment(0);//默认未评论
        orders.setCreatetime(new Date(System.currentTimeMillis()));//创建订单时间
        orders.setUpdatetime(new Date(System.currentTimeMillis()));//订单更新时间
        //现在还差userId,recipient,address,phone,goodsId,goodsName,
        // specName,price,updatetime,image
        Integer goodsId,specId;
        //想办法根据specId 获得 goodsId,再根据goodsId获得goods的所有信息
        specId = addOrderBO.getGoodsDetailId();
        Spec spec = orderMapper.getSpec(specId);
        orders.setSpecName(spec.getSpecName());//规格名称
        orders.setPrice(spec.getUnitPrice());//该规格下的价格

        goodsId = spec.getGoodsId();
        Goods goods = orderMapper.getGoods(goodsId);
        orders.setGoodsId(goodsId);//商品id
        orders.setGoodsName(goods.getName());//商品名
        orders.setImage(goods.getImage());//商品图片
        //根据nickname获得所有user的信息
        String nickname = addOrderBO.getToken();
        User user = orderMapper.getUser(nickname);
        orders.setUserId(user.getId());//用户id
        orders.setRecipient(user.getRecipient());//收件人
        orders.setAddress(user.getAddress());//收货地址
        orders.setPhone(user.getPhone());//手机号码

        //把 orders 装进 数据库中
        orderMapper.addOrder(orders);
        session.commit();
        session.close();
    }

    /**
     * 清空购物车
     */
    @Override
    public void settleAccounts(CartListBO cartListBO) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        //只需要改变订单状态status就好了
        List<CartBO> cartBOList = cartListBO.getCartList();
        for (CartBO cartBO : cartBOList) {
            Integer id =cartBO.getId();
            orderMapper.updateState(id);//根据订单id 改变 订单状态state
        }
        session.commit();
        session.close();
    }

    /**
     * 确认付款
     */
    @Override
    public void pay(int id) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        orderMapper.updateState(id);//根据订单id 改变 订单状态state
        session.commit();
        session.close();
    }

    @Override
    public void confirmReceive(int id) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        orderMapper.confirmReceive(id);//根据订单id 改变 订单状态state
        session.commit();
        session.close();
    }

    @Override
    public void sendComment(CommentBO commentBO) {
        //拿到 Mapper
        SqlSession session = MybatisUtils.getSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        orderMapper.updateHasComment(commentBO.getOrderId());//根据订单id 改变hasComment的值
        Comment comment = new Comment();

        comment.setNickname(commentBO.getToken());//用户昵称
        comment.setGoodsId(commentBO.getGoodsId());//商品id
        comment.setComment(commentBO.getContent());//评价的内容
        comment.setScore(commentBO.getScore());//评价打分
        comment.setTime(new Date(System.currentTimeMillis()));//评价时间

        User user = orderMapper.getUser(commentBO.getToken());
        comment.setUser(user);
        comment.setUserId(user.getId());//userId
        Spec spec = orderMapper.getSpec(commentBO.getGoodsDetailId());
        comment.setSpecName(spec.getSpecName());//specName

        orderMapper.sendComment(comment);

        session.commit();
        session.close();
    }
}
