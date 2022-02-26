package com.cskaoyan.mall.service;

import com.cskaoyan.mall.model.bean.Category;
import com.cskaoyan.mall.model.bean.Spec;
import com.cskaoyan.mall.model.bo.goodsBo.AskGoodsMsgBO;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsAddBO;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsUpdateBO;
import com.cskaoyan.mall.model.bo.messageBO.ReplyBO;
import com.cskaoyan.mall.model.vo.commentVO.CommentVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsByTypeVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsInfoVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsMsgVO;
import com.cskaoyan.mall.model.vo.messageVO.NoReplyMsgVO;
import com.cskaoyan.mall.model.vo.messageVO.RepliedMsgVO;

import java.util.List;

public interface GoodsService {

    List<Category> getType();

    List<GoodsByTypeVO> getGoodsByType(int categoryId);

    int addTypeByName(Category Category);//新增商品种类

    void addGoods(GoodsAddBO goodsAddBO);

    void deleteGoods(int id);

    GoodsInfoVO getGoodsInfo(int id);//根据商品id获取商品详情

    int addSpec(Spec spec);

    void deleteSpec(Spec spec);//按goodsId和specName删除规格

    void updateGoods(GoodsUpdateBO goodsUpdateBO);

    List<GoodsByTypeVO> searchGoods(String name);//根据商品名 查商品

    GoodsInfoVO getMallGoodsInfo(int id);//前台用户系统

    List<GoodsMsgVO> getGoodsMsg(int goodsId);//按商品id 查询 商品问答

    void askGoodsMsg(AskGoodsMsgBO askGoodsMsgBO);

    List<NoReplyMsgVO> noReplyMsg();

    List<RepliedMsgVO> repliedMsg();

    void reply(ReplyBO replyBO);

    CommentVO getGoodsComment(int goodsId);
}
