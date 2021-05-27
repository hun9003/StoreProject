package com.muesli.dao;


import com.muesli.domain.CommentBean;

import java.util.List;
import java.util.Map;

public interface CommentDAO {

    int getCommentCount(CommentBean commentBean);

    List<CommentBean> getCommentList(Map<String, Object> listMap);

    int createComment(CommentBean commentBean);

    int getMaxNum();

    int getMaxOrder(int cmt_num);

    CommentBean getComment(int cmt_id);

    int deleteComment(int cmt_id);
}
