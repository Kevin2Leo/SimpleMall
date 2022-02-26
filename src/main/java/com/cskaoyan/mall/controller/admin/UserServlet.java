package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.model.vo.adminVo.AllAdminsVO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.userVo.AllUsersVO;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.service.UserServiceImpl;
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

@WebServlet("/api/admin/user/*")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    ObjectMapper objectMapper = new ObjectMapper();
    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //→ /api/admin/user 把属于管理员模块的长长的URI给缩简化
        //  反正他们的 /api/admin/user 是相同的
        //值得到 他最后的 访问uri
        String action = requestURI.replace("/api/admin/user/", "");
        if("allUser".equals(action)){
            allUser(request, response);
        }
        if ("searchUser".equals(action)){
            searchUser(request, response);
        }
        if("deleteUser".equals(action)){
            deleteUser(request, response);
        }

    }

    /**
     *
     * 根据用户id删除用户
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数 id = 73
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        //根据用户id删除用户
        userService.deleteUserById(id);

        BaseRespVo baseRespVo = new BaseRespVo(0,null, null);
        //然后把 这个VO对象 转化为Json字符串
        String value = objectMapper.writeValueAsString(baseRespVo);
        //再把这个 json字符串 写入到响应体中
        response.getWriter().println(value);
    }

    /**
     * 根据用户昵称nickname查找该用户
     */
    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数 即 它的用户昵称
        String nickname = request.getParameter("word");
        //根据用户昵称查找 该用户的所有信息
        List<AllUsersVO> allUsersVOS= userService.searchUsers(nickname);
        BaseRespVo baseRespVo = new BaseRespVo(0,allUsersVOS, null);
        //然后把 这个VO对象 转化为Json字符串
        String value = objectMapper.writeValueAsString(baseRespVo);
        //再把这个 json字符串 写入到响应体中
        response.getWriter().println(value);
    }

    /**
     *  把user表里面的数据全部查询出来
     */
    private void allUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //把user表里面的数据全部查询出来
        List<AllUsersVO> usersVOS =  userService.allUsers();
        BaseRespVo result = new BaseRespVo(0,usersVOS, null);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
