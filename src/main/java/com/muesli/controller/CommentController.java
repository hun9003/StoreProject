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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Inject
    CommentService commentService;

    @Inject
    BoardService boardService;
    // 댓글 조회
    @RequestMapping(value = "/comment/{post_id}", method = RequestMethod.GET)
    public String comment(HttpSession session, HttpServletRequest request, Model model, @PathVariable int post_id) {
        System.out.println("CommentController - comment() :: GET /comment/" + post_id);

        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String brd_key = request.getParameter("brd_key");
        if(brd_key == null){
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        // 페이징 정보 저장
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page); // 서블릿에 붙은 페이지를 저장
        pageBean.setPageNum(page + "");

        String sizeStr = request.getParameter("size"); // 파라미터에 size가 있다면 (행 갯수 조절) 저장
        if (sizeStr != null && !sizeStr.equals("")) { // 사이즈가 따로 파라미터에 저장되어있지 않다면 15로 저장
            pageBean.setPageSize(Integer.parseInt(sizeStr));
        } else {
            pageBean.setPageSize(15);
        }
        PostBean postBean = boardService.getPost(post_id);

        CommentBean commentBean = new CommentBean();
        commentBean.setPost_id(post_id);
        commentBean.setCmt_del(0);

        Map<String, Object> ListMap = new HashMap<String, Object>();
        ListMap.put("comment", commentBean);

        // 페이지빈에 리스트의 총 갯수를 저장
        pageBean.setCount(commentService.getCommentCount(commentBean));
        pageBean.setStartRow((pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + 1 - 1);

        if (page < 1 || page > pageBean.getPageCount()) {
            model.addAttribute("isEmpty", true);
        } else {
            model.addAttribute("isEmpty", false);
        }

        ListMap.put("pageBean", pageBean);
        List<CommentBean> commentBeans = commentService.getCommentList(ListMap);

        model.addAttribute("commentList", commentBeans);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("postBean", postBean);
        model.addAttribute("brd_key", brd_key);

        return StrResources.COMMENT_PAGE;
    }

    // 댓글 생성
    @RequestMapping(value = "/comment/write", method = RequestMethod.POST)
    public String write_post(HttpSession session, HttpServletRequest request, Model model, CommentBean commentBean) {
        System.out.println("CommentController - write_post() :: POST /comment/write");

        String brd_key = request.getParameter("brd_key") != null ? request.getParameter("brd_key") : "notice";
        String url = "/board/"+brd_key+"/info/"+commentBean.getPost_id();

        PostBean postBean = boardService.getPost(commentBean.getPost_id());
        if(postBean == null) {
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        MemberBean memberBean = (MemberBean) session.getAttribute("member");


        if(commentBean.getCmt_content().trim().equals("")){
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        String nowIp = ScriptUtils.getIp(request);

        if(commentBean.getCmt_num() != 0) {
            commentBean.setCmt_reply(commentService.getMaxOrder(commentBean.getCmt_num()));
        }
        commentBean.setBrd_id(postBean.getBrd_id());
        commentBean.setCmt_num(commentBean.getCmt_num() == 0 ? commentService.getMaxNum()+1 : commentBean.getCmt_num());
        commentBean.setMem_id(memberBean.getMem_id());
        commentBean.setCmt_datetime(nowTime);
        commentBean.setCmt_updated_datetime(nowTime);
        commentBean.setCmt_ip(nowIp);
        commentBean.setCmt_like(0);
        commentBean.setCmt_dislike(0);
        commentBean.setCmt_blame(0);
        commentBean.setCmt_del(0);

        int result = commentService.createComment(commentBean);

        postBean.setPost_comment_count(commentService.getCommentCount(commentBean));
        postBean.setPost_comment_updated_datetime(nowTime);
        boardService.updateCommentCount(postBean);

        if(result == 1) {
            model.addAttribute("msg", StrResources.SUCCESS_COMMENT_WRITE);
            model.addAttribute("url", url);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        return StrResources.ALERT_MESSAGE_PAGE;
    }

    // 댓글 생성
    @RequestMapping(value = "/comment/delete", method = RequestMethod.GET)
    public String delete(HttpSession session, HttpServletRequest request, Model model) {
        System.out.println("CommentController - delete() :: GET /comment/delete");
        int cmt_id = 0;
        if(request.getParameter("cmt_id") != null) {
            cmt_id = Integer.parseInt(request.getParameter("cmt_id"));
        } else {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        MemberBean memberBean = (MemberBean)session.getAttribute("member");

        CommentBean commentBean = commentService.getComment(cmt_id);
        if(commentBean.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        int result = commentService.deleteComment(cmt_id);

        String brd_key = request.getParameter("brd_key") != null ? request.getParameter("brd_key") : "notice";
        String url = "/board/"+brd_key+"/info/"+commentBean.getPost_id();

        if(result == 1) {
            model.addAttribute("msg", StrResources.SUCCESS_COMMENT_DELETE);
            model.addAttribute("url", url);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        return StrResources.ALERT_MESSAGE_PAGE;
    }
}
