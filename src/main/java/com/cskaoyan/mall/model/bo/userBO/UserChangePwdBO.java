package com.cskaoyan.mall.model.bo.userBO;


public class UserChangePwdBO {

    private Integer id;//对应的是用户id
    private String oldPwd;//旧密码
    private String newPwd;//新密码
    private String confirmPwd;//把新密码 再输入一次的验证密码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }
}
