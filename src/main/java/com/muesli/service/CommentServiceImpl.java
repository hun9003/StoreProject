package com.muesli.service;

import com.muesli.dao.CommentDAO;
import com.muesli.domain.CommentBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

	@Inject
	CommentDAO commentDAO;


	@Override
	public int getCommentCount(CommentBean commentBean) {
		System.out.println("CommentServiceImpl - getCommentCount()");
		return  commentDAO.getCommentCount(commentBean);
	}

	@Override
	public List<CommentBean> getCommentList(Map<String, Object> listMap) {
		System.out.println("CommentServiceImpl - getCommentList()");
		return  commentDAO.getCommentList(listMap);
	}

	@Override
	public int createComment(CommentBean commentBean) {
		System.out.println("CommentServiceImpl - createComment()");

		commentBean.setCmt_datetime(new Timestamp(System.currentTimeMillis()));
		commentBean.setCmt_updated_datetime(new Timestamp(System.currentTimeMillis()));
		commentBean.setCmt_like(0);
		commentBean.setCmt_dislike(0);
		commentBean.setCmt_blame(0);
		commentBean.setCmt_del(0);

		return  commentDAO.createComment(commentBean);
	}

	@Override
	public int getMaxNum() {
		System.out.println("CommentServiceImpl - getMaxNum()");
		return  commentDAO.getMaxNum();
	}

	@Override
	public int getMaxOrder(int cmt_num) {
		System.out.println("CommentServiceImpl - getMaxOrder()");
		return  commentDAO.getMaxOrder(cmt_num);
	}

	@Override
	public CommentBean getComment(int cmt_id) {
		System.out.println("CommentServiceImpl - getComment()");
		return  commentDAO.getComment(cmt_id);
	}

	@Override
	public int deleteComment(int cmt_id) {
		System.out.println("CommentServiceImpl - deleteComment()");
		return  commentDAO.deleteComment(cmt_id);
	}

	@Override
	public int getMemberCommentCount(int mem_id) {
		System.out.println("CommentServiceImpl - getMemberCommentCount()");
		return commentDAO.getMemberCommentCount(mem_id);
	}
}
