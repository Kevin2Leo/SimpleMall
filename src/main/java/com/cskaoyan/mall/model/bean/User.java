package com.cskaoyan.mall.model.bean;

/**
 *   "id": 1,
 *   "email": "admin",
 *   "nickname": "admin",
 *   "pwd": "admin",
 *   "recipient": "admin",
 *   "address": "admin",
 *   "phone": "11111111111"
 */
public class User {
    private Integer id;
    private String username;//用户名 对应email
    private String password;
    private String nickname;//昵称
    private String address;//收货地址
    private String phone;//电话号码
    private String recipient;//收货人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
