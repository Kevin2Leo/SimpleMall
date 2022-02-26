package com.cskaoyan.mall.model.vo.commentVO;


import com.cskaoyan.mall.model.bean.Comment;

import java.util.List;

public class CommentVO {
    private List<Comment> commentList;
    private Double rate;//好评率

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
