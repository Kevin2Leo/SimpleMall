package com.cskaoyan.mall.utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MybatisUtils {

    private static SqlSessionFactory factory;

    static {
        //读取配置文件，然后生成一个factory
        InputStream inputStream = SqlSessionFactory.class.getClassLoader().getResourceAsStream("mybatis.xml");
        factory = new SqlSessionFactoryBuilder().build(inputStream);
    }


    public static SqlSession getSession(){

        return factory.openSession();
    }
}
