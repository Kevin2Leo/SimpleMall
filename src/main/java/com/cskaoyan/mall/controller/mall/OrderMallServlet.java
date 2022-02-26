package com.cskaoyan.mall.controller.mall;

import com.cskaoyan.mall.model.bo.commentBO.CommentBO;
import com.cskaoyan.mall.model.bo.orderBO.AddOrderBO;
import com.cskaoyan.mall.model.bo.orderBO.CartBO;
import com.cskaoyan.mall.model.bo.orderBO.CartListBO;
import com.cskaoyan.mall.model.bo.orderBO.GetOrderByStateBO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.orderVO.MallOrdersVO;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.OrderServiceImpl;
import com.cskaoyan.mall.utils.HttpUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/mall/order/*")
public class OrderMallServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();
    ObjectMapper objectMapper = new ObjectMapper();

    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/order/", "");
        if ("getOrderByState".equals(action)){
            try {
                getOrderByState(request,response);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if ("addOrder".equals(action)){
            addOrder(request,response);
        }
        if ("settleAccounts".equals(action)){
            settleAccounts(request,response);
        }
        if ("pay".equals(action)){
            pay(request,response);
        }
        if ("confirmReceive".equals(action)){
            confirmReceive(request,response);
        }
        if ("deleteOrder".equals(action)){
            deleteOrder(request,response);
        }
        if("sendComment".equals(action)){
            sendComment(request,response);
        }
    }

    /**
     * 商品评价
     */
    private void sendComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        CommentBO commentBO = objectMapper.readValue(requestBody, CommentBO.class);

        orderService.sendComment(commentBO);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);
    }

    /**
     * 删除订单
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        orderService.deleteOrder(id);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);
    }

    /**
     * 确认收货
     */
    private void confirmReceive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        orderService.confirmReceive(id);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);
    }

    private void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        orderService.pay(id);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);
    }

    /**
     * 购物车下单
     */
    private void settleAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        CartListBO cartListBO = objectMapper.readValue(requestBody, CartListBO.class);
        orderService.settleAccounts(cartListBO);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);
    }

    /**
     * 添加订单，立即购买 或者是 加入购物车
     */
    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //将json字符串 转换 成AddOrderBO
        AddOrderBO addOrderBO = objectMapper.readValue(requestBody, AddOrderBO.class);
        orderService.addOrder(addOrderBO);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);

    }

    private void getOrderByState(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        //获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        GetOrderByStateBO getOrderByStateBO = new GetOrderByStateBO();
        //利用BeanUtils的populate方法
        BeanUtils.populate(getOrderByStateBO,map);
        List<MallOrdersVO> mallOrdersVOList  = orderService.getOrderByState(getOrderByStateBO);

        BaseRespVo baseRespVo = new BaseRespVo(0, mallOrdersVOList, null);
        String value = objectMapper.writeValueAsString(baseRespVo);
        response.getWriter().println(value);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
