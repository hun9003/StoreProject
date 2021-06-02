package com.muesli.dao;

import com.muesli.domain.CommentBean;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDAOImpl implements CommentDAO {
	
	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "com.muesli.mapper.CommentMapper";


	@Override
	public int getCommentCount(CommentBean commentBean) {
		System.out.println("CommentDAOImpl - getCommentCount()");
		return sqlSession.selectOne(namespace+".getCommentCount", commentBean);
	}

	@Override
	public List<CommentBean> getCommentList(Map<String, Object> listMap) {
		System.out.println("CommentDAOImpl - getCommentList()");
		return sqlSession.selectList(namespace+".getCommentList", listMap);
	}

	@Override
	public int createComment(CommentBean commentBean) {
		System.out.println("CommentDAOImpl - createComment()");
		return sqlSession.insert(namespace+".createComment", commentBean);
	}

	@Override
	public int getMaxNum() {
		System.out.println("CommentDAOImpl - getMaxNum()");
		return sqlSession.selectOne(namespace+".getMaxNum");
	}

	@Override
	public int getMaxOrder(int cmt_num) {
		System.out.println("CommentDAOImpl - getMaxOrder()");
		return sqlSession.selectOne(namespace+".getMaxOrder", cmt_num);
	}

	@Override
	public CommentBean getComment(int cmt_id) {
		System.out.println("CommentDAOImpl - getComment()");
		return sqlSession.selectOne(namespace+".getComment", cmt_id);
	}

	@Override
	public int deleteComment(int cmt_id) {
		System.out.println("CommentDAOImpl - deleteComment()");
		return sqlSession.update(namespace+".deleteComment", cmt_id);
	}

	@Override
	public int getMemberCommentCount(int mem_id) {
		System.out.println("CommentDAOImpl - getMemberCommentCount()");
		return sqlSession.selectOne(namespace+".getMemberCommentCount", mem_id);
	}
}
