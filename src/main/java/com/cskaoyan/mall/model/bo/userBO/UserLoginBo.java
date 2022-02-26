package com.cskaoyan.mall.model.bo.userBO;


public class UserLoginBo {

    private String email;//对应username

    private String pwd;//对应password

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
