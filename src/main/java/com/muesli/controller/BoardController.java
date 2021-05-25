package com.muesli.controller;

import com.muesli.domain.*;
import com.muesli.service.BoardService;
import com.muesli.service.MemberService;
import com.muesli.util.ScriptUtils;
import com.muesli.util.StrResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {

    @Inject
    private BoardService boardService;

    @Inject
    private MemberService memberService;

    // 게시판 조회
    @RequestMapping(value = "/board/{brd_key}/{page}", method = RequestMethod.GET)
    public String board(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key, @PathVariable int page) {
        System.out.println("BoardController - board() :: GET /board/" + brd_key + "/" + page);

        String order_type = request.getParameter("order_type") != null ? request.getParameter("order_type") : "new";
        String search_type = request.getParameter("search_type");
        String search_content = request.getParameter("search_content");
        int isOnlyDel = 0;

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
        BoardBean boardBean = boardService.getBoardName(brd_key);

        Map<String, Object> ListMap = new HashMap<String, Object>();
        ListMap.put("search_type", search_type);
        ListMap.put("search_content", search_content);
        ListMap.put("isOnlyDel", isOnlyDel);
        ListMap.put("brd_id", boardBean.getBrd_id());

        // 페이지빈에 리스트의 총 갯수를 저장
        pageBean.setCount(boardService.getPostCount(ListMap));
        pageBean.setStartRow((pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + 1 - 1);

        if (page < 1 || page > pageBean.getPageCount()) {
            model.addAttribute("isEmpty", true);
        } else {
            model.addAttribute("isEmpty", false);
        }

        ListMap.put("pageBean", pageBean);
        ListMap.put("order_type", order_type);
        List<PostBean> postBeans = boardService.getPostList(ListMap);

        model.addAttribute("boardBean", boardBean);
        model.addAttribute("posts", postBeans);
        model.addAttribute("pageBean", pageBean);

        return StrResources.BOARD_PAGE;
    }

    // 게시물 생성
    @RequestMapping(value = "/board/{brd_key}/write", method = RequestMethod.GET)
    public String write(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key) {
        System.out.println("BoardController - write() :: GET /board/" + brd_key + "/write");
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        if(boardBean == null){
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(boardBean.getBrd_key().equals("notice")){
            if(memberBean.getMem_is_admin() != 1) {
                model.addAttribute("msg", StrResources.BAD_PERMISSION);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }

        model.addAttribute("boardBean", boardBean);
        return StrResources.BOARD_FORM_PAGE;
    }

    // 게시물 수정
    @RequestMapping(value = "/board/{brd_key}/update", method = RequestMethod.GET)
    public String update(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key) {
        System.out.println("BoardController - update() :: GET /board/" + brd_key + "/update");
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(request.getParameter("post_id") == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        int post_id = Integer.parseInt(request.getParameter("post_id"));

        MemberBean memberBean = (MemberBean) session.getAttribute("member");

        if(boardBean == null){
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(boardBean.getBrd_key().equals("notice")){
            if(memberBean.getMem_is_admin() != 1) {
                model.addAttribute("msg", StrResources.BAD_PERMISSION);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }

        PostBean postBean = boardService.getPost(post_id);
        if(postBean.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        model.addAttribute("boardBean", boardBean);
        model.addAttribute("postBean", postBean);
        return StrResources.BOARD_FORM_PAGE;
    }

    // 게시물 삭제
    @RequestMapping(value = "/board/{brd_key}/delete", method = RequestMethod.GET)
    public String delete(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key) {
        System.out.println("BoardController - delete() :: GET /board/" + brd_key + "/delete");
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(request.getParameter("post_id") == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        int post_id = Integer.parseInt(request.getParameter("post_id"));

        MemberBean memberBean = (MemberBean) session.getAttribute("member");

        if(boardBean == null){
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(boardBean.getBrd_key().equals("notice")){
            if(memberBean.getMem_is_admin() != 1) {
                model.addAttribute("msg", StrResources.BAD_PERMISSION);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }

        PostBean postBean = boardService.getPost(post_id);
        if(postBean.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        int result = boardService.deleteBoard(post_id);
        if(result == 0) {
            model.addAttribute("msg", StrResources.FAIL_BOARD_DELETE);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        model.addAttribute("msg", StrResources.SUCCESS_BOARD_DELETE);
        model.addAttribute("url", "/board/"+brd_key+"/1");
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    // 컨트롤러클래스의 로그를 출력
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    // 이미지 업로드
    // product_edit페이지에서 맵핑되는 메소드
    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
    // 이미지를 저장하고, 불러오고, 업로드하기위해 매개변수를 선언
    public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload)
    //MultipartFile 타입은 ckedit에서 upload란 이름으로 저장하게 된다
            throws Exception {

        // 한글깨짐을 방지하기위해 문자셋 설정
        response.setCharacterEncoding("utf-8");

        // 마찬가지로 파라미터로 전달되는 response 객체의 한글 설정
        response.setContentType("text/html; charset=utf-8");

        // 업로드한 파일 이름
        String fileName = upload.getOriginalFilename();

        // 파일을 바이트 배열로 변환
        byte[] bytes = upload.getBytes();

        // 이미지를 업로드할 디렉토리(배포 디렉토리로 설정)
        String root_path = request.getSession().getServletContext().getRealPath("/");
        String uploadPath = "resources/upload/";

//        프로젝트는 개발 디렉토리에 저장이 되는데 이미지를 업로드할 디렉토리를 개발 디렉토리로 설정하면 일일이 새로고침을 해주어야되서
//
//        불편하기 때문에 이미지를 업로드할 디렉토리를 배포 디렉토리로 설정한다.
        OutputStream out = new FileOutputStream(new File(root_path + uploadPath + fileName));

        // 서버로 업로드
        // write메소드의 매개값으로 파일의 총 바이트를 매개값으로 준다.
        // 지정된 바이트를 출력 스트립에 쓴다 (출력하기 위해서)
        out.write(bytes);

        // 클라이언트에 결과 표시
        String callback = request.getParameter("CKEditorFuncNum");
        System.out.println(callback);
        // 서버=>클라이언트로 텍스트 전송(자바스크립트 실행)
        PrintWriter printWriter = response.getWriter();
        String fileUrl = request.getContextPath() + "/resources/upload/" + fileName;
        printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl
                + "','Image Upload Complete!')" + "</script>");
        printWriter.flush();
    }

    // 게시물 생성
    @RequestMapping(value = "/board/{brd_key}/write", method = RequestMethod.POST)
    public String write_post(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key, PostBean postBean) {
        System.out.println("BoardController - write_post() :: POST /board/" + brd_key + "/write");
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        if(boardBean == null){
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(boardBean.getBrd_key().equals("notice")){
            if(memberBean.getMem_is_admin() != 1) {
                model.addAttribute("msg", StrResources.BAD_PERMISSION);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }
        if(postBean.getPost_title().trim().equals("")){
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        String nowIp = ScriptUtils.getIp(request);

        postBean.setBrd_id(boardBean.getBrd_id());
        postBean.setPost_category("");
        postBean.setMem_id(memberBean.getMem_id());
        postBean.setPost_userid(memberBean.getMem_userid());
        postBean.setPost_nickname(memberBean.getMem_nickname());
        postBean.setPost_datetime(nowTime);
        postBean.setPost_updated_datetime(nowTime);
        postBean.setPost_update_mem_id(memberBean.getMem_id());
        postBean.setPost_comment_count(0);
        postBean.setPost_comment_updated_datetime(null);
        postBean.setPost_secret(0);
        postBean.setPost_notice(0);
        postBean.setPost_hit(0);
        postBean.setPost_like(0);
        postBean.setPost_dislike(0);
        postBean.setPost_ip(nowIp);
        postBean.setPost_blame(0);
        postBean.setPost_file(0);
        postBean.setPost_image(0);
        postBean.setPost_del(0);

        int result = boardService.createPost(postBean);
        if(result == 1) {
            model.addAttribute("msg", StrResources.SUCCESS_BOARD_WRITE);
            model.addAttribute("url", "/board/"+brd_key+"/1");
            return StrResources.ALERT_MESSAGE_PAGE;
        }


        return StrResources.BOARD_FORM_PAGE;
    }

    // 게시물 수정
    @RequestMapping(value = "/board/{brd_key}/update", method = RequestMethod.POST)
    public String update_post(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key, PostBean postBean) {
        System.out.println("BoardController - update() :: GET /board/" + brd_key + "/write");
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(request.getParameter("post_id") == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        int post_id = Integer.parseInt(request.getParameter("post_id"));

        MemberBean memberBean = (MemberBean) session.getAttribute("member");

        if(boardBean == null){
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        if(boardBean.getBrd_key().equals("notice")){
            if(memberBean.getMem_is_admin() != 1) {
                model.addAttribute("msg", StrResources.BAD_PERMISSION);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }

        PostBean postBean2 = boardService.getPost(post_id);
        if(postBean2.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        postBean.setPost_update_mem_id(memberBean.getMem_id());
        postBean.setPost_updated_datetime(nowTime);

        int result = boardService.updatePost(postBean);
        if(result == 0) {
            model.addAttribute("msg", StrResources.FAIL_BOARD_UPDATE);

        }
        model.addAttribute("msg", StrResources.SUCCESS_BOARD_UPDATE);
        model.addAttribute("url", "/board/"+brd_key+"/1");
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    // 게시판 조회
    @RequestMapping(value = "/board/{brd_key}/info/{post_id}", method = RequestMethod.GET)
    public String boardInfo(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key, @PathVariable int post_id, PageBean pageBean) {
        System.out.println("BoardController - boardInfo() :: GET /board/" + brd_key + "/info");

        BoardBean boardBean = boardService.getBoardName(brd_key);
        PostBean postBean = boardService.getPost(post_id);
        int page = 1;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }
        pageBean.setCurrentPage(page);
        if(postBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        boardService.hitPost(post_id);

        int like = boardService.getLikeCount(post_id);
        int hate = boardService.getHateCount(post_id);
        postBean.setPost_like(like);
        postBean.setPost_dislike(hate);
        boardService.setLikeCount(postBean);
        boardService.setHateCount(postBean);


        Map<String, Object> likeMap = new HashMap<String, Object>();
        likeMap.put("member", (MemberBean)session.getAttribute("member"));
        likeMap.put("post", postBean);
        likeMap.put("ip", ScriptUtils.getIp(request));

        LikedBean likedBean = boardService.getLiked(likeMap);

        model.addAttribute("likedBean", likedBean);
        model.addAttribute("boardBean", boardBean);
        model.addAttribute("postBean", postBean);
        model.addAttribute("pageBean", pageBean);
        return StrResources.BOARD_INFO_PAGE;
    }

    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public ResponseEntity<String> like(HttpServletRequest request, HttpSession session) {
        System.out.println("BoardController - like() :: GET /like");
        MemberBean memberBean = (MemberBean)session.getAttribute("member");
        String result;
        ResponseEntity<String> entity;

        if(memberBean == null) {
            result = "nologin";
            try {
                entity = new ResponseEntity<String>(result, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        }
        int post_id = Integer.parseInt(request.getParameter("post_id"));
        PostBean postBean = boardService.getPost(post_id);

        Map<String, Object> likeMap = new HashMap<String, Object>();
        likeMap.put("member", memberBean);
        likeMap.put("post", postBean);
        likeMap.put("ip", ScriptUtils.getIp(request));
        try {
            LikedBean likedBean = boardService.getLiked(likeMap);
            likeMap.put("like", likedBean);
            if(likedBean != null) {
                if(likedBean.getLik_type() == 1) {
                    // 추천을 이미 했음 ( 추천 삭제 )
                    likeMap.put("type", "like");
                    boardService.deleteLike(likeMap);
                    result = "likeOff";
                } else {
                    // 비추천을 해놨음 ( 비추천 삭제후 추천 ).
                    likeMap.put("type", "hate");
                    boardService.deleteLike(likeMap);
                    likeMap.put("type", "like");
                    boardService.insertLike(likeMap);
                    result = "likeToggle";
                }
            } else {
                likeMap.put("type", "like");
                boardService.insertLike(likeMap);
                result = "likeOn";
            }
            entity = new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/hate", method = RequestMethod.GET)
    public ResponseEntity<String> hate(HttpServletRequest request, HttpSession session) {
        System.out.println("BoardController - hate() :: GET /hate");
        MemberBean memberBean = (MemberBean)session.getAttribute("member");
        String result;
        ResponseEntity<String> entity;

        if(memberBean == null) {
            result = "nologin";
            try {
                entity = new ResponseEntity<String>(result, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        }
        int post_id = Integer.parseInt(request.getParameter("post_id"));
        PostBean postBean = boardService.getPost(post_id);

        Map<String, Object> likeMap = new HashMap<String, Object>();
        likeMap.put("member", memberBean);
        likeMap.put("post", postBean);
        likeMap.put("ip", ScriptUtils.getIp(request));
        try {
            LikedBean likedBean = boardService.getLiked(likeMap);
            likeMap.put("like", likedBean);
            if(likedBean != null) {
                if(likedBean.getLik_type() == 2) {
                    // 비추천을 이미 했음 ( 비추천 삭제 )
                    likeMap.put("type", "hate");
                    boardService.deleteLike(likeMap);
                    result = "hateOff";
                } else {
                    // 비추천을 해놨음 ( 추천 삭제후 비추천 ).
                    likeMap.put("type", "like");
                    boardService.deleteLike(likeMap);
                    likeMap.put("type", "hate");
                    boardService.insertLike(likeMap);
                    result = "hateToggle";
                }
            } else {
                likeMap.put("type", "hate");
                boardService.insertLike(likeMap);
                result = "hateOn";
            }
            entity = new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
