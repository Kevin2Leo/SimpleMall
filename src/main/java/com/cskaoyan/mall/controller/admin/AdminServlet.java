package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.model.bo.adminBo.*;
import com.cskaoyan.mall.model.vo.adminVo.AdminLoginVo;
import com.cskaoyan.mall.model.vo.adminVo.AllAdminsVO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.cskaoyan.mall.service.AdminService;
import com.cskaoyan.mall.service.AdminServiceImpl;
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


/**
 * 后台管理员模块
 */

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {

    private AdminService adminService = new AdminServiceImpl();
    ObjectMapper objectMapper = new ObjectMapper();

    {
        //把值为null的值忽略掉
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        //→ /api/admin/admin 把属于管理员模块的长长的URI给缩简化
        //  反正他们的 /api/admin/admin 是相同的
        //值得到 他最后的 访问uri
        String action = requestURI.replace("/api/admin/admin/", "");
        if ("login".equals(action)) {//说明这是个登录请求
            login(request, response);//那就执行登录方法
        }
        if ("allAdmins".equals(action)) {
            allAdmins(request, response);
        }
        if ("getSearchAdmins".equals(action)) {
            getSearchAdmins(request, response);
        }
        if ("addAdminss".equals(action)) {
            addAdminss(request, response);
        }
        if ("deleteAdmins".equals(action)) {
            deleteAdmins(request, response);
        }
        if ("updateAdminss".equals(action)) {
            updateAdminss(request, response);
        }
        if ("getAdminsInfo".equals(action)) {
            getAdminsInfo(request, response);
        }
        if ("changePwd".equals(action)) {
            changePwd(request, response);
        }
        if ("logoutAdmin".equals(action)){
            logoutAdmin(request, response);
        }
    }

    private void logoutAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //2.将获取的json字符串 转换成java对象
        LogoutBO logoutBO = objectMapper.readValue(requestBody, LogoutBO.class);

        request.getSession().setAttribute("email", null);
        BaseRespVo result = new BaseRespVo(0,null,null);

        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String requestBody = HttpUtils.getRequestBody(request);
        //2.将获取的json字符串 转换成java对象
        AdminChangePwdBO adminChangePwdBO = objectMapper.readValue(requestBody, AdminChangePwdBO.class);
        int count = adminService.changPwd(adminChangePwdBO);
        if (count == 0) {//表示修改成功
            BaseRespVo result = new BaseRespVo(0,null, null);
            response.getWriter().println(objectMapper.writeValueAsString(result));
        } else if (count == 222) {//表示旧密码对不上
            BaseRespVo result = new BaseRespVo(10000,null, "旧密码错误！");
            response.getWriter().println(objectMapper.writeValueAsString(result));
        } else if (count == 333) {//表示newPwd 和 confirmPwd
            BaseRespVo result = new BaseRespVo(10000,null, "请保证两次输入的新密码一致！");
            response.getWriter().println(objectMapper.writeValueAsString(result));
        }

    }

    /**
     * 得到管理员的信息
     */
    private void getAdminsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.  获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);
        //2. 根据管理员id 获取管理员的详细信息
        AllAdminsVO adminsInfo = adminService.getAdminsInfo(id);

        BaseRespVo result = new BaseRespVo(0,adminsInfo, null);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 更新管理员的信息
     */
    private void updateAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数 以json数据形式呈现
        String requestBody = HttpUtils.getRequestBody(request);

        //2. 需要将字符串变成java对象
        AdminUpdateBo adminUpdateBo = objectMapper.readValue(requestBody, AdminUpdateBo.class);
        //按请求参数 更新 管理员信息
        adminService.updateAdmin(adminUpdateBo);
        BaseRespVo result = new BaseRespVo(0,null, null);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    /**
     * 按 id 删除管理员
     */
    private void deleteAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.  获取请求参数
        String i = request.getParameter("id");
        int id = Integer.parseInt(i);

        //执行删除方法 按id值删除
        adminService.deleteAdmins(id);

        BaseRespVo result = new BaseRespVo(0,null, null);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    private void addAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数 以json数据形式呈现
        String requestBody = HttpUtils.getRequestBody(request);
        //2. 需要将字符串变成java对象
        AdminAddBo adminAddBo = objectMapper.readValue(requestBody, AdminAddBo.class);

        //用户名 email(username)不能重复
        int code = adminService.addAdmins(adminAddBo);
        if (code == 0) {
            //说明添加成功
            BaseRespVo result = new BaseRespVo(0,null, null);
            response.getWriter().println(objectMapper.writeValueAsString(result));
        } else {
            //说明用户名重复
            BaseRespVo result = new BaseRespVo(10000,null, "该账号不允许重复使用");
            response.getWriter().println(objectMapper.writeValueAsString(result));
        }
    }

    /**
     * 多条件查询
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getSearchAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1.获取请求参数 以json数据形式呈现
        String requestBody = HttpUtils.getRequestBody(request);

        //2. 需要将字符串变成java对象
        AdminSearchBo adminSearchBo = objectMapper.readValue(requestBody, AdminSearchBo.class);

        //把admin表里面的数据全部查询出来
        List<AllAdminsVO> allAdminsVOS = adminService.searchAdmins(adminSearchBo);
        BaseRespVo result = new BaseRespVo(0,allAdminsVOS, null);

//        BaseRespVo result = BaseRespVo.ok(allAdminsVOS);

        response.getWriter().println(objectMapper.writeValueAsString(result));

    }

    //总结一下，每个方法应该是三步走
    //1.获取请求参数
    //2.具体业务逻辑处理
    //3.做出响应
    private void allAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //把admin表里面的数据全部查询出来
        List<AllAdminsVO> adminsVOS = adminService.allAdmins();
        BaseRespVo result = new BaseRespVo(0,adminsVOS, null);
        response.getWriter().println(objectMapper.writeValueAsString(result));
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //如何去分析
//        System.out.println("陈卓 加油啊 项目一");
        //1.如何获得请求参数？
        //不可以使用之前的request.getParameter 因为它不是key=value而是json字符串
        //1.获取请求参数 以json数据形式呈现
        String requestBody = HttpUtils.getRequestBody(request);
        //2. 需要将字符串变成java对象

        AdminLoginBo loginBo = objectMapper.readValue(requestBody, AdminLoginBo.class);

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
        int code = adminService.login(loginBo);
        //code 就是controller和service层的标准
        //code=200就表示成功,code=404用户名或密码错误,code=500服务器繁忙
        if (code == 200) {

            //设置session
            request.getSession().setAttribute("email",loginBo.getEmail());

            AdminLoginVo adminLoginVo = new AdminLoginVo();
            adminLoginVo.setToken(loginBo.getEmail());
            adminLoginVo.setName(loginBo.getEmail());
            BaseRespVo baseRespVo = new BaseRespVo(0,adminLoginVo, null);
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

