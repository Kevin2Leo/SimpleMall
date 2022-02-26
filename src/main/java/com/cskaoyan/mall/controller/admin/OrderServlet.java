package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.model.bo.orderBO.ChangeOrderBO;
import com.cskaoyan.mall.model.bo.orderBO.OrdersByPagesBO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.orderVO.OrdersByPagesVO;
import com.cskaoyan.mall.model.vo.orderVO.ShowVO;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.OrderServiceImpl;
import com.cskaoyan.mall.utils.HttpUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/order/*")
public class OrderServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();
    ObjectMapper objectMapper = new ObjectMapper();

    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        //→ /api/admin/order 把属于管理员模块的长长的URI给缩简化
        //  反正他们的 /api/admin/order 是相同的
        //值得到 他最后的 访问uri
        String action = requestURI.replace("/api/admin/order/", "");
        if("ordersByPage".equals(action)){
            ordersByPage(request, response);
        }
        else if("deleteOrder".equals(action)){
            deleteOrder(request,response);
        }
        else if("order".equals(action)){
            order(request,response);
        }
        else if("changeOrder".equals(action)){
            changeOrder(request,response);
        }

    }

    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        ChangeOrderBO changeOrderBO = objectMapper.readValue(requestBody, ChangeOrderBO.class);
        orderService.changeOrder(changeOrderBO);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        //得到的VO对象转换为json字符串
        String value = objectMapper.writeValueAsString(baseRespVo);
        //再将其写入响应体中
        response.getWriter().println(value);
    }

    private void order(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        ShowVO showVO =orderService.order(id);

        BaseRespVo baseRespVo = new BaseRespVo(0, showVO, null);
        //得到的VO对象转换为json字符串
        String value = objectMapper.writeValueAsString(baseRespVo);
        //再将其写入响应体中
        response.getWriter().println(value);
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);
        orderService.deleteOrder(id);

        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        //得到的VO对象转换为json字符串
        String value = objectMapper.writeValueAsString(baseRespVo);
        //再将其写入响应体中
        response.getWriter().println(value);
    }

    /**
     * 分页展示订单
     */
    private void ordersByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //将json字符串 转换成 BO对象
        OrdersByPagesBO ordersByPagesBO = objectMapper.readValue(requestBody, OrdersByPagesBO.class);

        OrdersByPagesVO ordersByPagesVO= orderService.orderByPage(ordersByPagesBO);

        BaseRespVo baseRespVo = new BaseRespVo(0, ordersByPagesVO, null);
        //得到的VO对象转换为json字符串
        String value = objectMapper.writeValueAsString(baseRespVo);
        //再将其写入响应体中
        response.getWriter().println(value);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
