package com.cskaoyan.mall.service;

import com.cskaoyan.mall.model.bo.adminBo.*;
import com.cskaoyan.mall.model.vo.adminVo.AllAdminsVO;

import java.util.List;

/**
 *
 */
public interface AdminService {

    int login(AdminLoginBo adminLoginBo);

    List<AllAdminsVO> allAdmins();

    List<AllAdminsVO> searchAdmins(AdminSearchBo adminSearchBo);

    int addAdmins(AdminAddBo adminAddBo);

    void deleteAdmins(int id);

    void updateAdmin(AdminUpdateBo adminUpdateBo);

    AllAdminsVO getAdminsInfo(int id);

    int changPwd(AdminChangePwdBO adminChangePwdBO);//修改管理员自己的密码
}
