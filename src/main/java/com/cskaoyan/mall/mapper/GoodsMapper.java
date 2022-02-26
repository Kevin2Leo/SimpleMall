package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.model.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {

    List<Category> getType();

    List<Goods> getGoodsByType(int categoryId);

    int existForCategoryName(String name);//该商品类名是否已经存在

    void addType(Category category);//增加商品种类

    void addGoods(Goods goods);//增加商品

    void addSpec(List<Spec> list);//增加商品规格

    void deleteById(int id);//根据商品id 删除商品

    Goods getGoodsInfo(int id);//根据id 获取所有的goods属性

    List<Spec> getSpecInfo(int goodsId);//根据goodsId获取该商品的所有规格

    int existForSpecName(Spec spec);//判断规格名是否有重名的情况

    void addSpec1(Spec spec);//只添加一个商品规格

    void deleteSpec(Spec spec);//按goodsId和specName删除规格

    void updateGoods(Goods goods);

    //下面这个一次性 更新多条数据的方法无法实现
    void updateSpecs(List<Spec> specs);

    void deleteSpecByGoodsId(int goodsId);//删除商品的时候 也要删除商品的规格

    //需要修改别名 不要和表名相同
    void updateSpec(@Param("s") Spec spec);

    List<Goods> searchGoods(String name);

    List<Message> getGoodsMsg(int goodsId);

    void askGoodsMsg(Message message);//添加一条 问答数据

    List<Message> noReplyMsg();

    List<Message> repliedMsg();

    void reply(Message message);

    List<Comment> getGoodsComment(int goodsId);
}

