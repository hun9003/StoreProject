package com.muesli.dao;

import com.muesli.domain.BoardBean;
import com.muesli.domain.LikedBean;
import com.muesli.domain.PostBean;

import java.util.List;
import java.util.Map;

public interface BoardDAO {

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

    int createPost(PostBean postBean);

	PostBean getPost(int post_id);

	int updatePost(PostBean postBean);

	void hitPost(int post_id);

	LikedBean getLiked(Map<String, Object> likeMap);

	void deleteLike(Map<String, Object> likeMap);

	void insertLike(Map<String, Object> likeMap);

	int getLikeCount(int post_id);

	int getHateCount(int post_id);

	void setLikeCount(PostBean postBean);

	void setHateCount(PostBean postBean);

    void updateCommentCount(PostBean postBean);

    int deletePost(int post_id);

    int getMemberPostCount(int mem_id);
}
