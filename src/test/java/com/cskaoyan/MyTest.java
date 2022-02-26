package com.cskaoyan;


import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.model.bean.Message;
import com.cskaoyan.mall.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

public class MyTest {



    public static void main(String[] args) {
        //在数据库中进行操作
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);


        Message message = new Message();
        message.setUserId(4);
        message.setGoodsId(4);
        message.setContent("其味无穷");
        message.setState(1);
        message.setCreatetime(new Date(System.currentTimeMillis()));
        message.setGoodsName("旺仔牛奶");
        message.setUsername("82163216@qq.com");
        message.setNickname("chenz");

        goodsMapper.askGoodsMsg(message);

        session.commit();
        session.close();

    }
}
