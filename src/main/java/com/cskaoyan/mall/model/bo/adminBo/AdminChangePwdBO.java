package com.cskaoyan.mall.model.bo.adminBo;

/**
 * 用于接收 更新密码信息的类
 */
public class AdminChangePwdBO {

    private String adminToken;//对应的是管理员昵称
    private String oldPwd;//旧密码
    private String newPwd;//新密码
    private String confirmPwd;//把新密码 再输入一次的验证密码

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
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
