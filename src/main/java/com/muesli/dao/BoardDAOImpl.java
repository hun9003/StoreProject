package com.muesli.dao;

import com.muesli.domain.BoardBean;
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
}
