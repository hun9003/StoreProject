package com.muesli.dao;

import com.muesli.domain.BoardBean;
import com.muesli.domain.LikedBean;
import com.muesli.domain.PostBean;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "com.muesli.mapper.BoardMapper";

	@Override
	public List<BoardBean> getBoardList() {
		System.out.println("BoardDAOImpl - getBoardList()");
		return sqlSession.selectList(namespace+".getBoardList");
	}

	@Override
	public BoardBean getBoard(int brd_id) {
		System.out.println("BoardDAOImpl - getBoard()");
		return sqlSession.selectOne(namespace+".getBoard", brd_id);
	}

	@Override
	public int getMaxOrder(int bgr_id) {
		System.out.println("BoardDAOImpl - getMaxOrder()");
		return sqlSession.selectOne(namespace+".getMaxOrder", bgr_id);
	}

	@Override
	public int insertBoard(BoardBean boardBean) {
		System.out.println("BoardDAOImpl - insertBoard()");
		return sqlSession.insert(namespace+".insertBoard", boardBean);
	}

	@Override
	public int updateBoard(BoardBean boardBean) {
		System.out.println("BoardDAOImpl - updateBoard()");
		return sqlSession.update(namespace+".updateBoard", boardBean);
	}

	@Override
	public int deleteBoard(int brd_id) {
		System.out.println("BoardDAOImpl - deleteBoard()");
		return sqlSession.delete(namespace+".deleteBoard", brd_id);
	}

	@Override
	public void pushBoardOrder(BoardBean boardBean) {
		System.out.println("BoardDAOImpl - pushBoardOrder()");
		sqlSession.update(namespace+".pushBoardOrder", boardBean);
	}

	@Override
	public BoardBean getBoardName(String brd_key) {
		System.out.println("BoardDAOImpl - getBoardName()");
		return sqlSession.selectOne(namespace+".getBoardName", brd_key);
	}

	@Override
	public int getPostCount(Map<String, Object> listMap) {
		System.out.println("BoardDAOImpl - getPostCount()");
		return sqlSession.selectOne(namespace+".getPostCount", listMap);
	}

	@Override
	public List<PostBean> getPostList(Map<String, Object> listMap) {
		System.out.println("BoardDAOImpl - getPostList()");
		return sqlSession.selectList(namespace+".getPostList", listMap);
	}

	@Override
	public int createPost(PostBean postBean) {
		System.out.println("BoardDAOImpl - createPost()");
		return sqlSession.insert(namespace+".createPost", postBean);
	}

	@Override
	public PostBean getPost(int post_id) {
		System.out.println("BoardDAOImpl - getPost()");
		return sqlSession.selectOne(namespace+".getPost", post_id);
	}

	@Override
	public int updatePost(PostBean postBean) {
		System.out.println("BoardDAOImpl - updatePost()");
		return sqlSession.update(namespace+".updatePost", postBean);
	}

	@Override
	public void hitPost(int post_id) {
		System.out.println("BoardDAOImpl - hitPost()");
		sqlSession.update(namespace+".hitPost", post_id);
	}

	@Override
	public LikedBean getLiked(Map<String, Object> likeMap) {
		System.out.println("BoardDAOImpl - getLiked()");
		return sqlSession.selectOne(namespace+".getLiked", likeMap);
	}

	@Override
	public void deleteLike(Map<String, Object> likeMap) {
		System.out.println("BoardDAOImpl - deleteLike()");
		sqlSession.delete(namespace+".deleteLike", likeMap);
	}

	@Override
	public void insertLike(Map<String, Object> likeMap) {
		System.out.println("BoardDAOImpl - insertLike()");
		sqlSession.insert(namespace+".insertLike", likeMap);
	}

	@Override
	public int getLikeCount(int post_id) {
		System.out.println("BoardDAOImpl - getLikeCount()");
		return sqlSession.selectOne(namespace+".getLikeCount", post_id);
	}

	@Override
	public int getHateCount(int post_id) {
		System.out.println("BoardDAOImpl - getHateCount()");
		return sqlSession.selectOne(namespace+".getHateCount", post_id);
	}

	@Override
	public void setLikeCount(PostBean postBean) {
		System.out.println("BoardDAOImpl - setLikeCount()");
		sqlSession.update(namespace+".setLikeCount", postBean);
	}

	@Override
	public void setHateCount(PostBean postBean) {
		System.out.println("BoardDAOImpl - setHateCount()");
		sqlSession.update(namespace+".setHateCount", postBean);
	}
}
