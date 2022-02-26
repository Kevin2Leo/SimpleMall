package com.cskaoyan.mall.model.vo.userVo;

import com.cskaoyan.mall.model.vo.adminVo.AllAdminsVO;
import com.cskaoyan.mall.model.vo.adminVo.BaseRespVo;

import java.util.List;

/**
*   "id": 1,
*   "email": "admin",
*   "nickname": "admin",
*   "pwd": "admin",
*   "recipient": "admin",
*   "address": "admin",
*   "phone": "11111111111"
 */
public class AllUsersVO {
    private Integer id;
    private String email;
    private String nickname;
    private String pwd;
    private String recipient;
    private String address;
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
