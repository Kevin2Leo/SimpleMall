package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.model.bean.Admin;
import com.cskaoyan.mall.model.bean.User;

import java.util.List;

public interface UserMapper {


    List<User> allUsers();

    List<User> searchUsersByNickname(String nickname);//根据用户的 昵称 查找用户信息

    void deleteUserById(int id);//根据用户id删除用户


    int login(User user);

    int exist(User user);//判断新注册的用户名是否已经存在

    void signUpUser(User user);//注册新用户

    User getUserByNickname(String nickname);

    String getNickname(String username);//通过username获得nickname

    User data(String nickname);//根据用户昵称 查找用户信息

    int selectByIdAndPassword(User user);

    void changePwd(User user);
}
