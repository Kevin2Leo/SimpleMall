package com.cskaoyan.mall.service;

import com.cskaoyan.mall.model.bean.User;
import com.cskaoyan.mall.model.bo.userBO.UserChangePwdBO;
import com.cskaoyan.mall.model.bo.userBO.UserLoginBo;
import com.cskaoyan.mall.model.bo.userBO.UserSignUpBO;
import com.cskaoyan.mall.model.vo.userVo.AllUsersVO;
import com.cskaoyan.mall.model.vo.userVo.UserSignUpVO;

import java.util.List;

public interface UserService {

    List<AllUsersVO> allUsers();

    List<AllUsersVO> searchUsers(String nickname);//根据用户昵称等到用户信息

    void deleteUserById(int id);//根据用户id删除用户


    int login(UserLoginBo loginBo);

    UserSignUpVO signUpUser(UserSignUpBO userSignUpBO);

    String getNickname(String username);//通过username获得nickname

    AllUsersVO data(String nickname);//根据用户昵称等到用户信息

    int changPwd(UserChangePwdBO userChangePwdBO);

}
