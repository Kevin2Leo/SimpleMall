package com.cskaoyan.mall.controller.mall;

import com.cskaoyan.mall.model.bo.goodsBo.AskGoodsMsgBO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.commentVO.CommentVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsByTypeVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsInfoVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsMsgVO;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.GoodsServiceImpl;
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

@WebServlet("/api/mall/goods/*")
public class GoodsMallServlet extends HttpServlet {

    private GoodsService goodsService = new GoodsServiceImpl();
    ObjectMapper objectMapper = new ObjectMapper();
    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/goods/", "");
        if ("getGoodsByType".equals(action)){
            getGoodsByType(request,response);
        }
        if ("searchGoods".equals(action)){
            searchGoods(request,response);
        }
        if ("getGoodsInfo".equals(action)) {
            getGoodsInfo(request, response);
        }
        if ("getGoodsMsg".equals(action)){
            getGoodsMsg(request,response);
        }
        if ("askGoodsMsg".equals(action)){
            askGoodsMsg(request,response);
        }
        if ("getGoodsComment".equals(action)){
            getGoodsComment(request,response);
        }

    }

    private void getGoodsComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("goodsId");//这是goodsId
        int goodsId = Integer.parseInt(i);

        CommentVO commentVO = goodsService.getGoodsComment(goodsId);

        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, commentVO, null)));

    }

    private void askGoodsMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //将json 封装成BO
        AskGoodsMsgBO askGoodsMsgBO = objectMapper.readValue(requestBody, AskGoodsMsgBO.class);
        //将这个提问 添加到数据库中


        goodsService.askGoodsMsg(askGoodsMsgBO);

        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, null, null)));

    }

    /**
     * 得到商品的问答信息
     */
    private void getGoodsMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("id");//这是goodsId
        int goodsId = Integer.parseInt(i);

        List<GoodsMsgVO> goodsMsgVOList = goodsService.getGoodsMsg(goodsId);

        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, goodsMsgVOList, null)));

    }

    /**
     * 得到商品的详情信息
     */
    private void getGoodsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取商品的id
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        GoodsInfoVO goodsInfo = goodsService.getMallGoodsInfo(id);
        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, goodsInfo, null)));

    }

    /**
     * 按商品名称搜索商品
     */
    private void searchGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String name = request.getParameter("keyword");//就是商品名称

        List<GoodsByTypeVO> goodsList = goodsService.searchGoods(name);

        BaseRespVo result = new BaseRespVo(0, goodsList, null);

        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("typeId");

        int categoryId = Integer.parseInt(i);

        List<GoodsByTypeVO> goodsList = goodsService.getGoodsByType(categoryId);

        BaseRespVo result = new BaseRespVo(0, goodsList, null);

        response.getWriter().println(objectMapper.writeValueAsString(result));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
