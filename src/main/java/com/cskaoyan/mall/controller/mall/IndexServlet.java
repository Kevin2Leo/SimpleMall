package com.cskaoyan.mall.controller.mall;

import com.cskaoyan.mall.model.bean.Category;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsByTypeVO;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.GoodsServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/mall/index/*")
public class IndexServlet extends HttpServlet {
    private GoodsService goodsService = new GoodsServiceImpl();
    ObjectMapper objectMapper = new ObjectMapper();
    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/index/", "");
        if ("getType".equals(action)) {
            getType(request, response);
        }

    }

    private void getType(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //把商品的分类 和 id 都给显示出来
        List<Category> categoryList = goodsService.getType();
        BaseRespVo result = new BaseRespVo(0, categoryList, null);
        response.getWriter().println(objectMapper.writeValueAsString(result));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
