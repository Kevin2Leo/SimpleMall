package com.cskaoyan.mall.service;

import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.model.bean.Admin;
import com.cskaoyan.mall.model.bo.adminBo.*;
import com.cskaoyan.mall.model.vo.adminVo.AllAdminsVO;
import com.cskaoyan.mall.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员页面 的service实现类
 */
public class AdminServiceImpl implements AdminService{

    /**
     * 登录方法
     * @param adminLoginBo 登录的对象 包括email和pwd
     * @return
     */
    @Override
    public int login(AdminLoginBo adminLoginBo) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);

        //将loginBo 的数据 转换成 Admin对象
        Admin admin = new Admin();
        admin.setUsername(adminLoginBo.getEmail());
        admin.setPassword(adminLoginBo.getPwd());

        try {
            int count = adminMapper.login(admin);
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


    /**
     * 查询所有 展示所有，这个界面是 登录后自动访问的
     * @return
     */
    //service层就是做和当前业务逻辑最息息相关的部分,脏话累活都是service层在做
    @Override
    public List<AllAdminsVO> allAdmins() {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);
        Admin ad = new Admin();
        List<Admin> adminList= adminMapper.allAdmins(ad);
        //将 List<Admin> 转换成 List<AllAdminsVO>
        List<AllAdminsVO> list = new ArrayList<AllAdminsVO>();
        for (Admin admin : adminList) {
            AllAdminsVO allAdminsVO = new AllAdminsVO();
            allAdminsVO.setId(admin.getId());
            allAdminsVO.setEmail(admin.getUsername());
            allAdminsVO.setNickname(admin.getNickname());
            allAdminsVO.setPwd(admin.getPassword());
            list.add(allAdminsVO);
        }
        session.commit();
        session.close();
        return list;
    }


    /**
     * 多条件查询，按管理员账号 或者 管理员昵称查询
     * @return
     */
    @Override
    public List<AllAdminsVO> searchAdmins(AdminSearchBo adminSearchBo){
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);
        //将adminSearchBo 的数据 转换成 Admin对象

        Admin admin = new Admin();
        admin.setUsername(adminSearchBo.getEmail());
        admin.setNickname(adminSearchBo.getNickname());

        List<Admin> adminList= adminMapper.allAdmins(admin);

        //将 List<Admin> 转换成 List<AllAdminsVO>
        List<AllAdminsVO> list = new ArrayList<AllAdminsVO>();

        for (Admin admin1 : adminList) {
            AllAdminsVO allAdminsVO = new AllAdminsVO();
            allAdminsVO.setId(admin1.getId());
            allAdminsVO.setEmail(admin1.getUsername());
            allAdminsVO.setNickname(admin1.getNickname());
            allAdminsVO.setPwd(admin1.getPassword());
            list.add(allAdminsVO);
        }
        session.commit();
        session.close();
        return list;
    }

    @Override
    public int addAdmins(AdminAddBo adminAddBo) {
        //获取adminMapper 与数据库建立联系
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);
        //将adminAddBo 的数据 转换成 Admin对象
        Admin admin = new Admin();
        admin.setNickname(adminAddBo.getNickname());
        admin.setUsername(adminAddBo.getEmail());
        admin.setPassword(adminAddBo.getPwd());

        //先判断一下是否 重名
        int count = adminMapper.exist(admin);
        if (count != 0){//如果存在重名
            session.commit();
            session.close();
            return 10000;//0表示添加成功 10000表示添加失败
        }
        boolean b = adminMapper.addAdmin(admin);
        session.commit();
        session.close();
        return 0;//0表示添加成功 10000表示添加失败
    }

    @Override
    public void deleteAdmins(int id) {
        //获取adminMapper 与数据库建立联系
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);

        adminMapper.deleteAdminById(id);

        session.commit();
        session.close();
    }

    @Override
    public void updateAdmin(AdminUpdateBo adminUpdateBo) {
        //获取adminMapper 与数据库建立联系
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);

        //将adminAddBo 的数据 转换成 Admin对象

        Admin admin = new Admin();
        admin.setId(adminUpdateBo.getId());
        admin.setNickname(adminUpdateBo.getNickname());
        admin.setUsername(adminUpdateBo.getEmail());
        admin.setPassword(adminUpdateBo.getPwd());

        adminMapper.updateAdmin(admin);

        session.commit();//提交事务
        session.close(); //关闭资源

    }

    @Override
    public AllAdminsVO getAdminsInfo(int id) {
        //获取adminMapper 与数据库建立联系
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);

        Admin adminInfo = adminMapper.getAdminInfo(id);
        //将 Admin 转换成 AllAdminsVO
        AllAdminsVO allAdminsVO = new AllAdminsVO();

        allAdminsVO.setId(adminInfo.getId());
        allAdminsVO.setEmail(adminInfo.getUsername());
        allAdminsVO.setNickname(adminInfo.getNickname());
        allAdminsVO.setPwd(adminInfo.getPassword());
        session.commit();
        session.close();

        return allAdminsVO;

    }

    @Override
    public int changPwd(AdminChangePwdBO adminChangePwdBO) {
        //获取adminMapper 与数据库建立联系
        SqlSession session = MybatisUtils.getSession();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);

        //1.先判断newPwd 和 confirmPwd 是否一致
        if(!adminChangePwdBO.getNewPwd().equals(adminChangePwdBO.getConfirmPwd()) ){
            //如果不一致
            session.commit();
            session.close();
            return 333;
        }
        Admin admin = new Admin();
        admin.setNickname(adminChangePwdBO.getAdminToken());
        admin.setPassword(adminChangePwdBO.getOldPwd());
        int count = adminMapper.selectByPasswordAndNickname(admin);
        if (count <= 0 ){//说明昵称对的上的情况下 密码对不上，说明旧密码输错了
            session.commit();
            session.close();
            return 222;
        }
        //如果上面两项都没问题，则可以修改密码了
        admin.setPassword(adminChangePwdBO.getNewPwd());//把admin中的密码换成新密码
        adminMapper.changePwd(admin);
        session.commit();
        session.close();
        return 0;
    }

}
