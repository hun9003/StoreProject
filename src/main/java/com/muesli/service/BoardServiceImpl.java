package com.muesli.service;

import com.muesli.dao.BoardDAO;
import com.muesli.domain.BoardBean;
import com.muesli.domain.PostBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	BoardDAO boardDAO;

	@Override
	public List<BoardBean> getBoardList() {
		System.out.println("BoardServiceImpl - getBoardList()");
		return boardDAO.getBoardList();
	}

	@Override
	public BoardBean getBoard(int brd_id) {
		System.out.println("BoardServiceImpl - getBoardList()");
		return boardDAO.getBoard(brd_id);
	}

	@Override
	public int getMaxOrder(int bgr_id) {
		System.out.println("BoardServiceImpl - getMaxOrder()");
		return boardDAO.getMaxOrder(bgr_id);
	}

	@Override
	public int insertBoard(BoardBean boardBean) {
		System.out.println("BoardServiceImpl - insertBoard()");
		return boardDAO.insertBoard(boardBean);
	}

	@Override
	public int updateBoard(BoardBean boardBean) {
		System.out.println("BoardServiceImpl - updateBoard()");
		return boardDAO.updateBoard(boardBean);
	}

	@Override
	public int deleteBoard(int brd_id) {
		System.out.println("BoardServiceImpl - deleteBoard()");
		return boardDAO.deleteBoard(brd_id);
	}

	@Override
	public void pushBoardOrder(BoardBean boardBean) {
		System.out.println("BoardServiceImpl - pushBoardOrder()");
		boardDAO.pushBoardOrder(boardBean);
	}

	@Override
	public BoardBean getBoardName(String brd_key) {
		System.out.println("BoardServiceImpl - getBoardName()");
		return boardDAO.getBoardName(brd_key);
	}

	@Override
	public int getPostCount(Map<String, Object> listMap) {
		System.out.println("BoardServiceImpl - getPostCount()");
		return boardDAO.getPostCount(listMap);
	}

	@Override
	public List<PostBean> getPostList(Map<String, Object> listMap) {
		System.out.println("BoardServiceImpl - getPostList()");
		return boardDAO.getPostList(listMap);
	}
}
