package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.model.bean.Category;
import com.cskaoyan.mall.model.bean.Spec;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsAddBO;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsUpdateBO;
import com.cskaoyan.mall.model.bo.messageBO.ReplyBO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsByTypeVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsInfoVO;
import com.cskaoyan.mall.model.vo.messageVO.NoReplyMsgVO;
import com.cskaoyan.mall.model.vo.messageVO.RepliedMsgVO;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.GoodsServiceImpl;
import com.cskaoyan.mall.utils.FileUploadUtils;
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
import java.util.Map;

@WebServlet("/api/admin/goods/*")
public class GoodsServlet extends HttpServlet {

    private GoodsService goodsService = new GoodsServiceImpl();

    ObjectMapper objectMapper = new ObjectMapper();

    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1,获取请求的URI
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        //判断请求的URI属于哪个，就执行哪个方法
        if ("getType".equals(action)) {
            getType(request, response);
        }
        if ("getGoodsByType".equals(action)) {
            getGoodsByType(request, response);
        }
        if ("imgUpload".equals(action)) {
            imgUpload(request, response);
        }
        if ("addType".equals(action)) {
            addType(request, response);
        }
        if ("addGoods".equals(action)) {
            addGoods(request, response);
        }
        if ("deleteGoods".equals(action)) {
            deleteGoods(request, response);
        }
        if ("getGoodsInfo".equals(action)) {
            getGoodsInfo(request, response);
        }
        if ("addSpec".equals(action)) {
            addSpec(request, response);
        }
        if ("deleteSpec".equals(action)) {
            deleteSpec(request, response);
        }
        if ("updateGoods".equals(action)){
            updateGoods(request,response);
        }
        if ("noReplyMsg".equals(action)){
            noReplyMsg(request,response);
        }
        if ("repliedMsg".equals(action)){
            repliedMsg(request,response);
        }
        if ("reply".equals(action)){
            reply(request,response);
        }
    }

    private void reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        ReplyBO replyBO = objectMapper.readValue(requestBody, ReplyBO.class);
        goodsService.reply(replyBO);

        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0,null,null)));

    }

    private void repliedMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<RepliedMsgVO> repliedMsgVOList= goodsService.repliedMsg();

        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0,repliedMsgVOList,null)));

    }

    private void noReplyMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<NoReplyMsgVO> noReplyMsgVOList= goodsService.noReplyMsg();

        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0,noReplyMsgVOList,null)));

    }

    private void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1，获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //将获取的json字符串封装成java对象
        GoodsUpdateBO goodsUpdateBO = objectMapper.readValue(requestBody, GoodsUpdateBO.class);

        //执行更新商品信息的方法
        goodsService.updateGoods(goodsUpdateBO);
        //更新成功 响应成功信息
        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0,null,null)));
    }

    private void deleteSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //将获得的json字符串 转化成Spec对象
        Spec spec = objectMapper.readValue(requestBody, Spec.class);
        //执行删除方法
        goodsService.deleteSpec(spec);
        //删除成功之后 返回"code":0
        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0,null,null)));
    }

    private void addSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //json字符串 封装成 对应的SpecAddBO
        Spec spec = objectMapper.readValue(requestBody, Spec.class);
        int code = goodsService.addSpec(spec);
        if (code == 10000) {//说明新增的规格的名 和 之前的重复
            response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(10000, null, "分类重复！")));
            return;
        } else {
            response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, spec, null)));
        }

    }

    /**
     * 得到商品的详情信息
     */
    private void getGoodsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取商品的id
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        GoodsInfoVO goodsInfo = goodsService.getGoodsInfo(id);
        response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, goodsInfo, null)));
    }

    /**
     * 根据商品id删除商品
     */
    private void deleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取商品id值
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        //执行删除方法
        goodsService.deleteGoods(id);

        //删除成功返回 结果 {"code":0}
        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        //将对象转换成 json字符串形式
        String json = objectMapper.writeValueAsString(baseRespVo);
        //将得到的json字符串 写入响应体中
        response.getWriter().println(json);

    }

    private void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数 json字符串
        String requestBody = HttpUtils.getRequestBody(request);
        GoodsAddBO goodsAddBO = null;

        try {
            //2.将json字符串 封装到BO对象中
            goodsAddBO = objectMapper.readValue(requestBody, GoodsAddBO.class);
        } catch (Exception e) {
            response.getWriter().println(objectMapper.writeValueAsString(new BaseRespVo(0, null, "参数不合法")));
            return;
        }
        //3.执行添加方法
        goodsService.addGoods(goodsAddBO);
        //添加成功返回 结果 {"code":0}
        BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
        //将对象转换成 json字符串形式
        String json = objectMapper.writeValueAsString(baseRespVo);
        //将得到的json字符串 写入响应体中
        response.getWriter().println(json);

    }

    private void addType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //将json 字符串 封装成javaBean
        Category category = objectMapper.readValue(requestBody, Category.class);

        int code = goodsService.addTypeByName(category);

        if (code == 10000) {//表示新增的商品类名 与 已经存在的 重复了
            BaseRespVo baseRespVo = new BaseRespVo(10000, null, "该种类已经存在");
            //然后把 这个VO对象 转化为Json字符串
            String value = objectMapper.writeValueAsString(baseRespVo);
            //再把这个 json字符串 写入到响应体中
            response.getWriter().println(value);
        } else if (code == 0) {

            BaseRespVo baseRespVo = new BaseRespVo(0, null, null);
            //然后把 这个VO对象 转化为Json字符串
            String value = objectMapper.writeValueAsString(baseRespVo);
            //再把这个 json字符串 写入到响应体中
            response.getWriter().println(value);
        }
    }

    private void imgUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用fileUpload来完成
        Map<String, Object> map = FileUploadUtils.parseRequest(request);

        String path = (String) map.get("file");

        response.getWriter().println(objectMapper.writeValueAsString(BaseRespVo.ok(path)));

    }

    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String i = request.getParameter("typeId");

        int categoryId = Integer.parseInt(i);

        List<GoodsByTypeVO> goodsList = goodsService.getGoodsByType(categoryId);

        BaseRespVo result = new BaseRespVo(0, goodsList, null);

        response.getWriter().println(objectMapper.writeValueAsString(result));

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
