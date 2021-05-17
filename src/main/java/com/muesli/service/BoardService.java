package com.muesli.service;

import com.muesli.domain.BoardBean;
import com.muesli.domain.PostBean;

import java.util.List;
import java.util.Map;

public interface BoardService {

	List<BoardBean> getBoardList();

	BoardBean getBoard(int brd_id);

	int getMaxOrder(int bgr_id);

	int insertBoard(BoardBean boardBean);

	int updateBoard(BoardBean boardBean);

	int deleteBoard(int brd_id);

	void pushBoardOrder(BoardBean boardBean);

	BoardBean getBoardName(String brd_key);

	int getPostCount(Map<String, Object> listMap);

	List<PostBean> getPostList(Map<String, Object> listMap);
}
