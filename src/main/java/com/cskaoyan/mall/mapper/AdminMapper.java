package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.model.bean.Admin;

import java.util.List;

public interface AdminMapper {

    int login(Admin admin);

    int exist(Admin admin);//判断admin在原表中是否存在 存在则返回大于0的值，不存在则返回0

    List<Admin> allAdmins(Admin admin);

    boolean addAdmin(Admin admin);

    void deleteAdminById(int id);//按id删除管理员账号

    void updateAdmin(Admin admin);

    Admin getAdminInfo(int id);

    int selectByPasswordAndNickname(Admin admin);//根据管理员的昵称 和 密码来查询

    void changePwd(Admin admin);
}


