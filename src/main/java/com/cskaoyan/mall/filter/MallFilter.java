package com.cskaoyan.mall.filter;


import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/api/mall/*")
public class MallFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //设置响应的授权
        String origin = (String) request.getServletContext().getAttribute("origin");
        response.setHeader("Access-Control-Allow-Origin",origin);
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials","true");
        //针对这些接口，你需要去判断是否放行
        //如何判断哪些接口要验证登录，哪些直接放行呢？
        // /api/admin/admin/login   其他全部需要验证权限
        String requestURI = request.getRequestURI();
        //对于options方法之外的方法才去判断
        String method = request.getMethod();
//        if(!"OPTIONS".equals(method)){
//            if(!"/api/admin/admin/login".equals(requestURI)){
//                //验证是否登录
//                HttpSession session = request.getSession();
//                System.out.println("filter:" + session + "id:" + session.getId() + "URI:" + requestURI);
//                Object email = session.getAttribute("email");
//                if(email == null){
//                    //没有登录  拦截
//                    response.getWriter().println(new ObjectMapper().writeValueAsString(BaseRespVo.error("当前接口仅登录后才可以访问")));
//                    return;
//                }
//            }
//        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
