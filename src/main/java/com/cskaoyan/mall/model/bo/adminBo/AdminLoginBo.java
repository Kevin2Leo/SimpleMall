package com.cskaoyan.mall.model.bo.adminBo;

/**
 * 用于接收用户登录请求 数据 的 类
 */
public class AdminLoginBo {

    private String email;//管理员邮箱

    private String pwd;//管理员密码

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
