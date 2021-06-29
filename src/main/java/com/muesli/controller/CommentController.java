package com.muesli.controller;

import com.muesli.domain.*;
import com.muesli.service.BoardService;
import com.muesli.service.CommentService;
import com.muesli.util.ScriptUtils;
import com.muesli.util.StrResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 댓글 관리를 위한 컨트롤러 클래스
 *
 * @author 개발자 박진훈
 * @version 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일         수정자           수정내용
 *  ------------   --------    ---------------------------
 *   2021.04.06     박진훈          최초 생성
 *
 * Copyright (C) 2009 by MOPAS  All right reserved.
 * </pre>
 * @since 2021.04.06
 */

@Controller
public class CommentController {

    @Inject
    CommentService commentService;

    @Inject
    BoardService boardService;

    /**
     * 댓글 리스트 출력하는 페이지
     *
     * @param model   뷰에 전달할 객체
     * @param post_id 게시물 PK
     * @param page    현재 페이지
     * @param brd_key 게시판 KEY
     * @param size    페이지 사이즈
     * @return "/page/comment"
     */
    @RequestMapping(value = "/comment/{post_id}", method = RequestMethod.GET)
    public String comment(Model model, @PathVariable int post_id,
                          @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                          @RequestParam(value = "brd_key", required = false) String brd_key,
                          @RequestParam(value = "size", defaultValue = "15", required = false) int size) {
        System.out.println("CommentController - comment() :: GET /comment/" + post_id);

        if (brd_key == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        // 페이징 빈 설정
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageNum(page + "");
        pageBean.setPageSize(size);

        /*
            게시물 KEY를 통해 게시물 정보를 호출한다
         */
        PostBean postBean = boardService.getPost(post_id);
        
        /*
        댓글 리스트 출력을 위해 각종 정보 저장
         */
        CommentBean commentBean = new CommentBean();
        commentBean.setPost_id(post_id);
        commentBean.setCmt_del(0);

        Map<String, Object> ListMap = new HashMap<>();
        ListMap.put("comment", commentBean);

        // 출력 리스트의 총 갯수 count
        pageBean.setCount(commentService.getCommentCount(commentBean));
        // 출력 리스트의 LIMIT 시작 번호 ( currentPage(현재페이지)-1 ) * getPageSize(페이지크기)+1-1
        pageBean.setStartRow((pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + 1 - 1);

        // 결과가 존재하지 않을 시 예외처리
        model.addAttribute("isEmpty", page < 1 || page > pageBean.getPageCount());

        ListMap.put("pageBean", pageBean);
        List<CommentBean> commentBeans = commentService.getCommentList(ListMap);

        /*
		VIEW 에 가져갈 객체 저장
		List<CommentBean> commentList = 댓글 리스트
		PostBean postBean = 게시판 정보
		PageBean pageBean = 페이지 빈
		String brd_key = 게시판 이름
		 */
        model.addAttribute("commentList", commentBeans);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("postBean", postBean);
        model.addAttribute("brd_key", brd_key);

        return StrResources.COMMENT_PAGE;
    }

    /**
     * 댓글 작성
     * @param session 세션
     * @param model 뷰에 뿌려줄 객체
     * @param request 요청
     * @param commentBean 댓글 정보
     * @param brd_key 게시판 KEY
     * @return "/common/alertMessage"
     */
    @RequestMapping(value = "/comment/write", method = RequestMethod.POST)
    public String write_post(HttpSession session, Model model, HttpServletRequest request, CommentBean commentBean,
                             @RequestParam(value = "brd_key", required = false) String brd_key) {
        System.out.println("CommentController - write_post() :: POST /comment/write");

        /*
		로그인 여부를 파악하는 메서드 호출 (로그인 상태 = return true)
		서블릿 메서드 MemberController.login()
		 */
        if (!StrResources.CHECK_LOGIN(session)) {
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
         세션으로 회원정보를 저장한다
         */
        MemberBean memberBean = (MemberBean) session.getAttribute("member");

        /*
        게시판 정보가 담겨있지 않으면 경고창 출력
         */
        if (brd_key == null) {
            model.addAttribute("msg", StrResources.BAD_REQUEST);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        되돌아갈 url 저장
         */
        String url = "/board/" + brd_key + "/info/" + commentBean.getPost_id();

        /*
        게시물 PK로 게시물 정보 가져오기
         */
        PostBean postBean = boardService.getPost(commentBean.getPost_id());
        if (postBean == null) {
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        댓글 내용이 없으면 경고창 출력
         */
        if (commentBean.getCmt_content().trim().equals("")) {
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        댓글에 답글을 작성했다면 답글 순서 저장
         */
        if (commentBean.getCmt_num() != 0) {
            commentBean.setCmt_reply(commentService.getMaxOrder(commentBean.getCmt_num()) + 1);
        }
        commentBean.setBrd_id(postBean.getBrd_id());

        /*
        답글이 아니라면 PK 그대로 입력 답글이라면 대상 댓글 PK 저장
         */
        commentBean.setCmt_num(commentBean.getCmt_num() == 0 ? commentService.getMaxNum() + 1 : commentBean.getCmt_num());
        commentBean.setMem_id(memberBean.getMem_id());
        commentBean.setCmt_ip(ScriptUtils.getIp(request));


        int result = commentService.createComment(commentBean);

        /*
        게시물에 댓글 업데이트 일자와 댓글 개수 초기화
         */
        postBean.setPost_comment_count(commentService.getCommentCount(commentBean));
        boardService.updateCommentCount(postBean);

        if (result == 1) {
            model.addAttribute("msg", StrResources.SUCCESS_COMMENT_WRITE);
            model.addAttribute("url", url);
        } else {
            model.addAttribute("msg", StrResources.FAIL_COMMENT_WRITE);
        }

        return StrResources.ALERT_MESSAGE_PAGE;
    }

    /**
     * 댓글 삭제
     * @param session 세션
     * @param model 뷰에 전달할 객체
     * @param cmt_id 댓글 PK
     * @param brd_key 게시판 KEY
     * @return "/common/alertMessage"
     */
    @RequestMapping(value = "/comment/delete", method = RequestMethod.GET)
    public String delete(HttpSession session, Model model,
                         @RequestParam(value = "cmt_id", defaultValue = "0", required = false) int cmt_id,
                         @RequestParam(value = "brd_key", required = false) String brd_key) {
        System.out.println("CommentController - delete() :: GET /comment/delete");
        /*
		로그인 여부를 파악하는 메서드 호출 (로그인 상태 = return true)
		서블릿 메서드 MemberController.login()
		 */
        if (!StrResources.CHECK_LOGIN(session)) {
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        
        /*
         세션으로 회원정보를 저장한다
         */
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        
        /*
        댓글 PK가 없으면 경고창 출력
         */
        if (cmt_id == 0) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        댓글 작성자인지 확인
         */
        CommentBean commentBean = commentService.getComment(cmt_id);
        if (commentBean.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        댓글 삭제
         */
        int result = commentService.deleteComment(cmt_id);

        /*
        게시판 정보가 담겨있지 않으면 경고창 출력
         */
        if (brd_key == null) {
            model.addAttribute("msg", StrResources.BAD_REQUEST);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        되돌아갈 url 저장
         */
        String url = "/board/" + brd_key + "/info/" + commentBean.getPost_id();

        if (result == 1) {
            model.addAttribute("msg", StrResources.SUCCESS_COMMENT_DELETE);
            model.addAttribute("url", url);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        return StrResources.ALERT_MESSAGE_PAGE;
    }
}
