package com.cskaoyan.mall.service;


import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.model.bean.*;
import com.cskaoyan.mall.model.bo.goodsBo.AskGoodsMsgBO;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsAddBO;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsAddSpecBO;
import com.cskaoyan.mall.model.bo.goodsBo.GoodsUpdateBO;
import com.cskaoyan.mall.model.bo.messageBO.ReplyBO;
import com.cskaoyan.mall.model.vo.commentVO.CommentVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsByTypeVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsInfoVO;
import com.cskaoyan.mall.model.vo.goodsVO.GoodsMsgVO;
import com.cskaoyan.mall.model.vo.messageVO.NoReplyMsgVO;
import com.cskaoyan.mall.model.vo.messageVO.RepliedMsgVO;
import com.cskaoyan.mall.model.vo.messageVO.Userrr;
import com.cskaoyan.mall.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsServiceImpl implements GoodsService {

    @Override
    public List<Category> getType() {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Category> categoryList = goodsMapper.getType();

        session.commit();
        session.close();
        return categoryList;
    }

    @Override
    public List<GoodsByTypeVO> getGoodsByType(int categoryId) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Goods> goodsList = goodsMapper.getGoodsByType(categoryId);

        //把得到的 List<Goods> 转换为 List<GoodsByTypeVO>
        List<GoodsByTypeVO> goodsByTypeVOList = new ArrayList<GoodsByTypeVO>();

        for (Goods goods : goodsList) {
            GoodsByTypeVO goodsByTypeVO = new GoodsByTypeVO();

            goodsByTypeVO.setId(goods.getId());
            goodsByTypeVO.setName(goods.getName());
            goodsByTypeVO.setTypeId(goods.getCategoryId());
            goodsByTypeVO.setImg(goods.getImage());
            goodsByTypeVO.setPrice(goods.getPrice());
            goodsByTypeVO.setStockNum(goods.getStockNum());
            goodsByTypeVOList.add(goodsByTypeVO);

        }
        session.commit();
        session.close();
        return goodsByTypeVOList;
    }

    /**
     * @param
     * @return 10000表示 该种类已存在，0表示 可以添加
     */
    @Override
    public int addTypeByName(Category category) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        String name = category.getName();
        int i = goodsMapper.existForCategoryName(name);//判断该商品类名 是否已经存在
        if (i > 0) {//说明该商品类名已经存在
            session.commit();
            session.close();
            return 10000;
        }
        //否则 可以添加
        goodsMapper.addType(category);
        session.commit();
        session.close();
        return 0;
    }

    /**
     * 商品的添加方法
     *
     * @param goodsAddBO
     */
    @Override
    public void addGoods(GoodsAddBO goodsAddBO) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        //1.先想办法得到商品的 price 和 stockNum
        //这里的price取的是所有该商品下规格价钱的最小值,stockNum取的是该商品所有规格库存的总和
        List<GoodsAddSpecBO> specList = goodsAddBO.getSpecList();

        int sum = 0;
        Double minn = 999999.00;
        for (GoodsAddSpecBO goodsAddSpecBO : specList) {

            sum += goodsAddSpecBO.getStockNum();//循环求和 求总库存量

            if (goodsAddSpecBO.getUnitPrice() < minn) {//打擂台 取最小值
                minn = goodsAddSpecBO.getUnitPrice();
            }

        }
        //把goodsAddBO里的数据 转化到 Goods中 和 Spec 中
        Goods goods = new Goods();

        goods.setName(goodsAddBO.getName());//转name
        goods.setImage(goodsAddBO.getImg());//转image
        goods.setDescription(goodsAddBO.getDesc());//转description
        goods.setCategoryId(goodsAddBO.getTypeId());//转商品类别 id
        goods.setPrice(minn);//设置商品价格price
        goods.setStockNum(sum);//设置商品总库存

        //现在就差 Spec 里的goodsId不知道怎么添加了
        goodsMapper.addGoods(goods);//Mapper方法添加
        //使用.xml中 useGeneratedKeys="true"  Column="id"
        //可以将新增的主键id值返回给goods对象
        List<Spec> list = new ArrayList<Spec>();
        for (GoodsAddSpecBO goodsAddSpecBO : specList) {

            Spec spec = new Spec();
            spec.setSpecName(goodsAddSpecBO.getSpecName());
            spec.setUnitPrice(goodsAddSpecBO.getUnitPrice());
            spec.setStockNum(goodsAddSpecBO.getStockNum());
            spec.setGoodsId(goods.getId());//所以这里可以得到该商品的id值
            list.add(spec);
        }
        goodsMapper.addSpec(list);
        session.commit();
        session.close();
    }

    /**
     * 根据商品id删除
     *
     * @param id
     */
    @Override
    public void deleteGoods(int id) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        goodsMapper.deleteById(id);

        //注意!!! 删除商品后 该商品对应的规格也要删除！！！
        //根据商品id → goodsId → 删除该商品的所有规格数据
        int goodsId = id;
        goodsMapper.deleteSpecByGoodsId(goodsId);

        session.commit();
        session.close();
    }

    /**
     * 根据商品id获取商品详细信息
     *
     * @param id
     * @return
     */
    @Override
    public GoodsInfoVO getGoodsInfo(int id) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        Goods goods = goodsMapper.getGoodsInfo(id);//根据商品id获取该商品的详细信息
        //将Goods 转换成 GoodsByTypeVO
        GoodsByTypeVO goodsByTypeVO = new GoodsByTypeVO();
//        "id": 746,
//         "img": "http://115.29.141.32:8084/static/image/1620973070891a.jpg",
//         "name": "华为手机",
//         "desc": "华为手机 就是好，不买后悔一辈子",
//         "typeId": 191,
//         "unitPrice": 0.0
        goodsByTypeVO.setId(goods.getId());
        goodsByTypeVO.setImg(goods.getImage());
        goodsByTypeVO.setName(goods.getName());
        goodsByTypeVO.setDesc(goods.getDescription());
        goodsByTypeVO.setTypeId(goods.getCategoryId());
        goodsByTypeVO.setUnitPrice(goods.getPrice());

        //根据商品id获取 该商品的所有规格
        int goodsId = id;
        List<Spec> specList = goodsMapper.getSpecInfo(goodsId);
        for (Spec spec : specList) {
            spec.setGoodsId(null);
        }
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();

        goodsInfoVO.setSpecs(specList);
        goodsInfoVO.setGoods(goodsByTypeVO);
        session.commit();
        session.close();
        return goodsInfoVO;
    }

    @Override
    public int addSpec(Spec spec) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        int count = goodsMapper.existForSpecName(spec);//先判断是否 有重名的情况
        if (count > 0) {//说明有重名的
            session.commit();
            session.close();
            return 10000;
        } else {//说明 可以添加
            goodsMapper.addSpec1(spec);
            session.commit();
            session.close();
            return 0;
        }

    }

    /**
     * 按goodsId和specName删除规格
     *
     * @param spec
     */
    @Override
    public void deleteSpec(Spec spec) {
        //获得goodsMapper
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        goodsMapper.deleteSpec(spec);

        session.commit();
        session.close();
    }

    @Override
    public void updateGoods(GoodsUpdateBO goodsUpdateBO) {
        //获取session 并得到 goodsMapper
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        //将GoodsUpdateBO里的数据 封装到Goods 和 List<Spec>中
        Goods goods = new Goods();

        goods.setId(goodsUpdateBO.getId());
        goods.setName(goodsUpdateBO.getName());
        goods.setCategoryId(goodsUpdateBO.getTypeId());
        goods.setImage(goodsUpdateBO.getImg());
        goods.setDescription(goodsUpdateBO.getDesc());

        List<Spec> specs = goodsUpdateBO.getSpecList();
        //开始执行 更新商品信息的方法
        goodsMapper.updateGoods(goods);

//        goodsMapper.updateSpecs(specs);这种一次更新多条数据 的方式无法实现

        //只能像下面一样 逐条更新
        for (Spec spec : specs) {
            goodsMapper.updateSpec(spec);
        }

        session.commit();
        session.close();
    }

    @Override
    public List<GoodsByTypeVO> searchGoods(String name) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Goods> goodsList = goodsMapper.searchGoods(name);

        //把得到的 List<Goods> 转换为 List<GoodsByTypeVO>
        List<GoodsByTypeVO> goodsByTypeVOList = new ArrayList<GoodsByTypeVO>();

        for (Goods goods : goodsList) {
            GoodsByTypeVO goodsByTypeVO = new GoodsByTypeVO();

            goodsByTypeVO.setId(goods.getId());
            goodsByTypeVO.setName(goods.getName());
            goodsByTypeVO.setTypeId(goods.getCategoryId());
            goodsByTypeVO.setImg(goods.getImage());
            goodsByTypeVO.setPrice(goods.getPrice());
            goodsByTypeVO.setStockNum(goods.getStockNum());
            goodsByTypeVOList.add(goodsByTypeVO);

        }
        session.commit();
        session.close();
        return goodsByTypeVOList;
    }

    @Override
    public GoodsInfoVO getMallGoodsInfo(int id) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        Goods goods = goodsMapper.getGoodsInfo(id);//根据商品id获取该商品的详细信息
        //将Goods 转换成 GoodsInfoVO
//        "id": 746,
//         "img": "http://115.29.141.32:8084/static/image/1620973070891a.jpg",
//         "name": "华为手机",
//         "desc": "华为手机 就是好，不买后悔一辈子",
//         "typeId": 191,
//         "unitPrice": 0.0
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();

        goodsInfoVO.setImg(goods.getImage());
        goodsInfoVO.setName(goods.getName());
        goodsInfoVO.setDesc(goods.getDescription());
        goodsInfoVO.setTypeId(goods.getCategoryId());
        goodsInfoVO.setUnitPrice(goods.getPrice());

        //根据商品id获取 该商品的所有规格
        int goodsId = id;
        List<Spec> specList = goodsMapper.getSpecInfo(goodsId);
        for (Spec spec : specList) {
            spec.setGoodsId(null);
        }
        goodsInfoVO.setSpecs(specList);
        session.commit();
        session.close();

        return goodsInfoVO;
    }

    @Override
    public List<GoodsMsgVO> getGoodsMsg(int goodsId) {
        //在数据库中进行查询
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Message> messageList = goodsMapper.getGoodsMsg(goodsId);

        //把得到的List<Message> 封装成 List<GoodsMsgVO>
        List<GoodsMsgVO> goodsMsgVOList = new ArrayList<GoodsMsgVO>();
        for (Message message : messageList) {
            GoodsMsgVO goodsMsgVO = new GoodsMsgVO();

            goodsMsgVO.setId(message.getId());
            goodsMsgVO.setAsker(message.getNickname());
            goodsMsgVO.setContent(message.getContent());
            goodsMsgVO.setTime(message.getCreatetime());

            Reply reply = new Reply();
            reply.setContent(message.getReplyContent());
            reply.setTime(message.getReplyTime());
            goodsMsgVO.setReply(reply);

            goodsMsgVOList.add(goodsMsgVO);
        }

        session.commit();
        session.close();
        return goodsMsgVOList;
    }


    @Override
    public void askGoodsMsg(AskGoodsMsgBO askGoodsMsgBO) {
        //在数据库中进行操作
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        //将BO 转化为 Message
        Message message = new Message();
        message.setGoodsId(askGoodsMsgBO.getGoodsId());
        message.setContent(askGoodsMsgBO.getMsg());
        message.setNickname(askGoodsMsgBO.getToken());

        message.setCreatetime(new Date(System.currentTimeMillis()));
        message.setState(1);//未回复是1 回复了是0

        //要想办法 拿到 userId , 通过nickname去拿
        User user = userMapper.getUserByNickname(askGoodsMsgBO.getToken());
        message.setUserId(user.getId());
        message.setUsername(user.getUsername());
        //想办法得到goodsName, 通过goodsId去拿
        Goods goods = goodsMapper.getGoodsInfo(askGoodsMsgBO.getGoodsId());
        message.setGoodsName(goods.getName());

        goodsMapper.askGoodsMsg(message);

        session.commit();
        session.close();
        return;
    }

    @Override
    public List<NoReplyMsgVO> noReplyMsg() {
        //在数据库中进行操作
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Message> messageList = goodsMapper.noReplyMsg();
        //把 List<Message> 转换为 List<NoReplyMsgVO>
        List<NoReplyMsgVO> noReplyMsgVOList = new ArrayList<NoReplyMsgVO>();
        for (Message message : messageList) {
            NoReplyMsgVO noReplyMsgVO = new NoReplyMsgVO();

            noReplyMsgVO.setId(message.getId());
            noReplyMsgVO.setUserId(message.getUserId());
            noReplyMsgVO.setGoodsId(message.getGoodsId());
            noReplyMsgVO.setContent(message.getContent());
            noReplyMsgVO.setState(message.getState());
            noReplyMsgVO.setCreatetime(message.getCreatetime());
            Goods goods = new Goods();
            goods.setName(message.getGoodsName());
            noReplyMsgVO.setGoods(goods);
            Userrr user = new Userrr();
            user.setName(message.getNickname());
            noReplyMsgVO.setUser(user);

            noReplyMsgVOList.add(noReplyMsgVO);

        }
        session.commit();
        session.close();
        return noReplyMsgVOList;
    }

    @Override
    public List<RepliedMsgVO> repliedMsg() {

        //在数据库中进行操作
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Message> messageList = goodsMapper.repliedMsg();
        //把 List<Message> 转换为 List<NoReplyMsgVO>
        List<RepliedMsgVO> repliedMsgVOList = new ArrayList<RepliedMsgVO>();

        for (Message message : messageList) {
            RepliedMsgVO repliedMsgVO = new RepliedMsgVO();

            repliedMsgVO.setId(message.getId());
            repliedMsgVO.setUserId(message.getUserId());
            repliedMsgVO.setGoodsId(message.getGoodsId());
            repliedMsgVO.setContent(message.getContent());
            repliedMsgVO.setReplyContent(message.getReplyContent());
            repliedMsgVO.setState(message.getState());
            repliedMsgVO.setCreatetime(message.getCreatetime());

            Goods goods = new Goods();
            goods.setName(message.getGoodsName());
            repliedMsgVO.setGoods(goods);
            Userrr user = new Userrr();
            user.setName(message.getNickname());
            repliedMsgVO.setUser(user);

            repliedMsgVOList.add(repliedMsgVO);

        }
        session.commit();
        session.close();
        return repliedMsgVOList;
    }

    @Override
    public void reply(ReplyBO replyBO) {
        //在数据库中进行操作
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        Message message = new Message();

        message.setId(replyBO.getId());
        message.setReplyContent(replyBO.getContent());
        message.setReplyTime(new Date(System.currentTimeMillis()));

        goodsMapper.reply(message);

        session.commit();
        session.close();
    }

    @Override
    public CommentVO getGoodsComment(int goodsId) {
        //在数据库中进行操作
        SqlSession session = MybatisUtils.getSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<Comment> commentList = goodsMapper.getGoodsComment(goodsId);

        Double rate, sum = 0.0, cnt = 0.0;//sum是好评个数  cnt是评价总数
        for (Comment comment : commentList) {
            User user = new User();
            user.setNickname(comment.getNickname());
            comment.setUser(user);
            comment.setNickname(null);
            comment.setGoodsId(null);
            if (comment.getScore() == 100.0) {//我设置的5星(即100分)才算好评
                sum += 1;
            }
            cnt += 1;
        }
        if (cnt > 0) {
            rate = sum * 100 / cnt;
        } else {
            rate = 0.0;
        }
        //然后来求 好评率

        CommentVO commentVO = new CommentVO();
        commentVO.setCommentList(commentList);
        commentVO.setRate(rate);

        session.commit();
        session.close();
        return commentVO;
    }

}


