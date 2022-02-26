package com.cskaoyan.mall.service;


import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.model.bean.Admin;
import com.cskaoyan.mall.model.bean.User;
import com.cskaoyan.mall.model.bo.userBO.UserChangePwdBO;
import com.cskaoyan.mall.model.bo.userBO.UserLoginBo;
import com.cskaoyan.mall.model.bo.userBO.UserSignUpBO;
import com.cskaoyan.mall.model.vo.userVo.AllUsersVO;
import com.cskaoyan.mall.model.vo.userVo.UserSignUpVO;
import com.cskaoyan.mall.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{

    /**
     * 展示所有
     * @return
     */
    //service层就是做和当前业务逻辑最息息相关的部分,脏话累活都是service层在做
    @Override
    public List<AllUsersVO> allUsers() {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        List<User> userList= userMapper.allUsers();
        //将 List<User> 转换成 List<AllUsersVO>
        List<AllUsersVO> list = new ArrayList<AllUsersVO>();
        for (User user : userList) {
            AllUsersVO allUsersVO = new AllUsersVO();

            allUsersVO.setId(user.getId());
            allUsersVO.setEmail(user.getUsername());
            allUsersVO.setNickname(user.getNickname());
            allUsersVO.setAddress(user.getAddress());
            allUsersVO.setPwd(user.getPassword());
            allUsersVO.setPhone(user.getPhone());
            allUsersVO.setRecipient(user.getRecipient());

            list.add(allUsersVO);
        }
        session.commit();
        session.close();
        return list;
    }

    /**
     * 按用户昵称查询
     * @param nickname
     * @return
     */
    @Override
    public List<AllUsersVO> searchUsers(String nickname) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        List<User> userList= userMapper.searchUsersByNickname(nickname);

        //把List<User> 转化成 List<AllUserVO>
        ArrayList<AllUsersVO> allUsersVOS = new ArrayList<AllUsersVO>();
        for (User user : userList) {
            AllUsersVO allUsersVO = new AllUsersVO();

            allUsersVO.setId(user.getId());
            allUsersVO.setEmail(user.getUsername());
            allUsersVO.setNickname(user.getNickname());
            allUsersVO.setAddress(user.getAddress());
            allUsersVO.setPwd(user.getPassword());
            allUsersVO.setPhone(user.getPhone());
            allUsersVO.setRecipient(user.getRecipient());

            allUsersVOS.add(allUsersVO);
        }
        session.commit();
        session.close();
        return allUsersVOS;
    }

    /**
     * 按用户id删除
     * @param id
     */
    @Override
    public void deleteUserById(int id) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        userMapper.deleteUserById(id);
        session.commit();
        session.close();
    }

    @Override
    public int login(UserLoginBo userLoginBo) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        //将userLoginBo 的数据 转换成 User对象
        User user = new User();
        user.setUsername(userLoginBo.getEmail());
        user.setPassword(userLoginBo.getPwd());

        try {
            int count = userMapper.login(user);
            if (count == 0){

                return 404;//用户或密码错误
                           //或提醒用户注册
            }else if (count == 1){

                return 200;//表示登录成功
            }
        } catch (Exception e) {

        }finally {
            session.commit();
            session.close();
        }
        return 500;//服务器繁忙
    }

    @Override
    public UserSignUpVO signUpUser(UserSignUpBO userSignUpBO) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        //将userSignUpBO 转换成 User对象
        User user = new User();

        user.setUsername(userSignUpBO.getEmail());
        user.setNickname(userSignUpBO.getNickname());
        user.setPassword(userSignUpBO.getPwd());
        user.setRecipient(userSignUpBO.getRecipient());
        user.setAddress(userSignUpBO.getAddress());
        user.setPhone(userSignUpBO.getPhone());
        //先判断一下是否 重名
        UserSignUpVO userSignUpVO = new UserSignUpVO();
        int count = userMapper.exist(user);
        if (count != 0){//如果存在重名
            session.commit();
            session.close();
            return null;
        }

        //把注册的数据添加到数据库中
        userMapper.signUpUser(user);

        userSignUpVO.setName(user.getNickname());
        userSignUpVO.setToken(user.getNickname());
        session.commit();
        session.close();
        return userSignUpVO;
    }

    @Override
    public String getNickname(String username) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        String nickname = userMapper.getNickname(username);

        session.commit();
        session.close();
        return nickname;
    }

    @Override
    public AllUsersVO data(String nickname) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        User user = userMapper.data(nickname);
        AllUsersVO usersVO = new AllUsersVO();

        usersVO.setId(user.getId());
        usersVO.setEmail(user.getUsername());
        usersVO.setNickname(nickname);
        usersVO.setRecipient(user.getRecipient());
        usersVO.setAddress(user.getAddress());
        usersVO.setPhone(user.getPhone());

        session.commit();
        session.close();
        return usersVO;
    }

    @Override
    public int changPwd(UserChangePwdBO userChangePwdBO) {
        //获取adminMapper 与数据库建立联系
        SqlSession session = MybatisUtils.getSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        //1.先判断newPwd 和 confirmPwd 是否一致
        if(!userChangePwdBO.getNewPwd().equals(userChangePwdBO.getConfirmPwd()) ){
            //如果不一致
            session.commit();
            session.close();
            return 333;
        }
        User user = new User();

        user.setId(userChangePwdBO.getId());
        user.setPassword(userChangePwdBO.getOldPwd());

        int code = userMapper.selectByIdAndPassword(user);
        if (code <= 0){//说明昵称对的上的情况下 密码对不上，说明旧密码输错了
            session.commit();
            session.close();
            return 222;
        }
        //如果上面两项都没问题，则可以修改密码了
        user.setPassword(userChangePwdBO.getNewPwd());//把user中的密码换成新密码
        userMapper.changePwd(user);
        session.commit();
        session.close();
        return 0;
    }


}
