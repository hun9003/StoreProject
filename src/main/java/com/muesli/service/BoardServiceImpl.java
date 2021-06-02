package com.muesli.service;

import com.muesli.dao.BoardDAO;
import com.muesli.domain.BoardBean;
import com.muesli.domain.LikedBean;
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

	@Override
	public int createPost(PostBean postBean) {
		System.out.println("BoardServiceImpl - createPost()");
		return boardDAO.createPost(postBean);
	}

	@Override
	public PostBean getPost(int post_id) {
		System.out.println("BoardServiceImpl - getPost()");
		return boardDAO.getPost(post_id);
	}

	@Override
	public int updatePost(PostBean postBean) {
		System.out.println("BoardServiceImpl - updatePost()");
		return boardDAO.updatePost(postBean);
	}

	@Override
	public void hitPost(int post_id) {
		System.out.println("BoardServiceImpl - hitPost()");
		boardDAO.hitPost(post_id);
	}

	@Override
	public LikedBean getLiked(Map<String, Object> likeMap) {
		System.out.println("BoardServiceImpl - getLiked()");
		return boardDAO.getLiked(likeMap);
	}

	@Override
	public void deleteLike(Map<String, Object> likeMap) {
		System.out.println("BoardServiceImpl - deleteLike()");
		boardDAO.deleteLike(likeMap);
	}

	@Override
	public void insertLike(Map<String, Object> likeMap) {
		System.out.println("BoardServiceImpl - insertLike()");
		boardDAO.insertLike(likeMap);
	}

	@Override
	public int getLikeCount(int post_id) {
		System.out.println("BoardServiceImpl - getLikeCount()");
		return boardDAO.getLikeCount(post_id);
	}

	@Override
	public int getHateCount(int post_id) {
		System.out.println("BoardServiceImpl - getHateCount()");
		return boardDAO.getHateCount(post_id);
	}

	@Override
	public void setLikeCount(PostBean postBean) {
		System.out.println("BoardServiceImpl - setLikeCount()");
		boardDAO.setLikeCount(postBean);
	}

	@Override
	public void setHateCount(PostBean postBean) {
		System.out.println("BoardServiceImpl - setHateCount()");
		boardDAO.setHateCount(postBean);
	}

	@Override
	public void updateCommentCount(PostBean postBean) {
		System.out.println("BoardServiceImpl - updateCommentCount()");
		boardDAO.updateCommentCount(postBean);
	}

	@Override
	public int deletePost(int post_id) {
		System.out.println("BoardServiceImpl - deletePost()");
		return boardDAO.deletePost(post_id);
	}

	@Override
	public int getMemberPostCount(int mem_id) {
		System.out.println("BoardServiceImpl - getMemberPostCount()");
		return boardDAO.getMemberPostCount(mem_id);
	}
}
