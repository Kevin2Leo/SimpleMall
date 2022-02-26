package com.cskaoyan.mall.model.bo.adminBo;

/**
 * 用于接收用户搜索管理员请求 数据 的 类
 */
public class AdminSearchBo {

    private String nickname;//管理员昵称

    private String email;//管理员账号(邮箱)


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
}
