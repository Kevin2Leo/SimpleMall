package com.cskaoyan.mall.controller.mall;

import com.cskaoyan.mall.model.bean.User;
import com.cskaoyan.mall.model.bo.adminBo.AdminChangePwdBO;
import com.cskaoyan.mall.model.bo.adminBo.AdminLoginBo;
import com.cskaoyan.mall.model.bo.userBO.UserChangePwdBO;
import com.cskaoyan.mall.model.bo.userBO.UserLoginBo;
import com.cskaoyan.mall.model.bo.userBO.UserSignUpBO;
import com.cskaoyan.mall.model.vo.adminVo.AdminLoginVo;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.model.vo.userVo.AllUsersVO;
import com.cskaoyan.mall.model.vo.userVo.UserLoginVO;
import com.cskaoyan.mall.model.vo.userVo.UserSignUpVO;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.service.UserServiceImpl;
import com.cskaoyan.mall.utils.HttpUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/mall/user/*")
public class UserMallServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    ObjectMapper objectMapper = new ObjectMapper();

    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/mall/user/", "");
        if ("login".equals(action)){
            login(request,response);
        }
        if ("signup".equals(action)){
            signup(request,response);
        }
        if ("data".equals(action)){
            data(request,response);
        }
        if ("updatePwd".equals(action)){
            updatePwd(request,response);
        }
    }

    private void updatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1.获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //2.将获取的json字符串 转换成java对象
        UserChangePwdBO userChangePwdBO = objectMapper.readValue(requestBody, UserChangePwdBO.class);
        int count = userService.changPwd(userChangePwdBO);


        if (count == 0) {//表示修改成功
            BaseRespVo result = new BaseRespVo(0,null, null);
            response.getWriter().println(objectMapper.writeValueAsString(result));
        } else if (count == 222) {//表示旧密码对不上
            BaseRespVo result = new BaseRespVo(1000,null, "旧密码错误！");
            response.getWriter().println(objectMapper.writeValueAsString(result));
        } else if (count == 333) {//表示newPwd 和 confirmPwd
            BaseRespVo result = new BaseRespVo(1000,null, "请保证两次输入的新密码一致！");
            response.getWriter().println(objectMapper.writeValueAsString(result));
        }



    }

    private void data(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String nickname = request.getParameter("token");

        AllUsersVO  usersVO = userService.data(nickname);

        BaseRespVo baseRespVo = new BaseRespVo(0,usersVO, null);
        response.getWriter().println(objectMapper.writeValueAsString(baseRespVo));
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //2. 需要将字符串变成java对象
        UserSignUpBO userSignUpBO = objectMapper.readValue(requestBody, UserSignUpBO.class);

        UserSignUpVO userSignUpVO= userService.signUpUser(userSignUpBO);

        if (userSignUpVO == null){
            BaseRespVo baseRespVo = new BaseRespVo(10000,null, "该账号已存在");
            response.getWriter().println(objectMapper.writeValueAsString(baseRespVo));
        }
        else {
            BaseRespVo baseRespVo = new BaseRespVo(0,userSignUpVO, null);
            response.getWriter().println(objectMapper.writeValueAsString(baseRespVo));
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        //2. 需要将字符串变成java对象

        UserLoginBo loginBo = objectMapper.readValue(requestBody, UserLoginBo.class);

        //这时候 也可以做一些校验
        if (StringUtils.isNullOrEmpty(loginBo.getEmail()) || StringUtils.isNullOrEmpty(loginBo.getPwd())) {
            //若管理员账号 或 密码 为空
            //  返回一个code值 不为 0
            BaseRespVo baseRespVo = new BaseRespVo(10000,null, "密码 或 用户名为空");
            //把java对象 转成 json字符串
            String value = objectMapper.writeValueAsString(baseRespVo);
            //在把json字符串 写入响应体中
            response.getWriter().println(value);
            return;
        }
        //通过校验
        //接下来 登录 至少有两种结果 1.成功
        // 2.失败(1用户名不存在,2密码错误,3服务器错误)
        int code = userService.login(loginBo);
        //code 就是controller和service层的标准
        //code=200就表示成功,code=404用户名或密码错误,code=500服务器繁忙
        if (code == 200) {

            //设置session
            request.getSession().setAttribute("email",loginBo.getEmail());

            UserLoginVO userLoginVO = new UserLoginVO();
            String nickname= userService.getNickname(loginBo.getEmail());
            userLoginVO.setName(nickname);
            userLoginVO.setToken(nickname);
            BaseRespVo baseRespVo = new BaseRespVo(0,userLoginVO, null);
            //把java对象 转成 json字符串
            String value = objectMapper.writeValueAsString(baseRespVo);
            //在把json字符串 写入响应体中
            response.getWriter().println(value);
            return;
        } else if (code == 404) {
            BaseRespVo baseRespVo = new BaseRespVo(10000,null, "密码 或 用户名错误");
            //把java对象 转成 json字符串
            String value = objectMapper.writeValueAsString(baseRespVo);
            //在把json字符串 写入响应体中
            response.getWriter().println(value);
        } else {//code = 500
            BaseRespVo baseRespVo = new BaseRespVo(10000,null, "服务器繁忙");
            //把java对象 转成 json字符串
            String value = objectMapper.writeValueAsString(baseRespVo);
            //在把json字符串 写入响应体中
            response.getWriter().println(value);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
