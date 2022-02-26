package com.cskaoyan.mall.model.bo.adminBo;

/**
 * 用于接收 更新管理员请求 数据 的 类
 */
public class AdminUpdateBo {

    private Integer id;
    private String nickname;
    private String email;
    private String pwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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
