package com.cskaoyan.mall.filter;

import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/admin/*")
public class ApplicationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //设置响应的授权
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials","true");
        //针对这些接口，你需要去判断是否放行
        //如何判断哪些接口需要验证登录 哪些直接放行呢？
        // /api/admin/admin/login不需要验证权限，其他全部需要验证权限
//        String requestURI = request.getRequestURI();
//        //对于 options方法之外的方法采取判断
//        String method = request.getMethod();
//        if (!"OPTIONS".equals(method)){
//            if (!"/api/admin/admin/login".equals(requestURI)){
//
//                //验证是否登录
//                Object email = request.getSession().getAttribute("email");
//                if (email == null ){//说明没有登录
//                    String value = new ObjectMapper().writeValueAsString(new BaseRespVo(0, null, "当前接口登录后才能访问"));
//                    response.getWriter().println(value);
//                    return;
//                }
//
//            }
//        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
