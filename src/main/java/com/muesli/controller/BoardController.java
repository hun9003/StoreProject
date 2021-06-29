package com.muesli.controller;

import com.muesli.domain.*;
import com.muesli.service.BoardService;
import com.muesli.service.MemberService;
import com.muesli.util.ScriptUtils;
import com.muesli.util.StrResources;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시판 관리를 위한 컨트롤러 클래스
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
public class BoardController {

    @Inject
    private BoardService boardService;

    @Inject
    private MemberService memberService;

    /**
     * 게시물 리스트 출력하는 페이지
     *
     * @param model          뷰에 전달할 객체
     * @param brd_key        게시판 KEY
     * @param page           현재 페이지
     * @param order_type     정렬 타입
     * @param search_type    검색 타입
     * @param search_content 검색 내용
     * @param size           페이지 사이즈
     * @return "/page/board"
     */
    @RequestMapping(value = "/board/{brd_key}", method = RequestMethod.GET)
    public String board(Model model, @PathVariable String brd_key,
                        @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                        @RequestParam(value = "order_type", defaultValue = "new", required = false) String order_type,
                        @RequestParam(value = "search_type", required = false) String search_type,
                        @RequestParam(value = "search_content", required = false) String search_content,
                        @RequestParam(value = "size", defaultValue = "15", required = false) int size) {
        System.out.println("BoardController - board() :: GET /board/" + brd_key);

        // 페이징 빈 설정
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageNum(page + "");
        pageBean.setPageSize(size);

        /*
            게시판 KEY를 통해 게시판 정보를 호출한다
            게시판 키가 올바르지 않다면 404 오류 출력
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if (boardBean == null) {
            model.addAttribute("msg", StrResources.PAGE_404);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
            삭제처리 되지 않은 게시물만 출력
         */
        int isOnlyDel = 0;

        /*
            리스트맵에 리스트 출력에 필요한 각종정보 전달
            sch_type = 검색 타입 ( 1 : 제목 + 내용, 2 : 제목, 3 : 내용, 4 : 닉네임 )
		    sch_content = 검색 내용
         */
        Map<String, Object> ListMap = new HashMap<>();
        ListMap.put("search_type", search_type);
        ListMap.put("search_content", search_content);
        ListMap.put("isOnlyDel", isOnlyDel);
        ListMap.put("brd_id", boardBean.getBrd_id());

        // 출력 리스트의 총 갯수 count
        pageBean.setCount(boardService.getPostCount(ListMap));
        // 출력 리스트의 LIMIT 시작 번호 ( currentPage(현재페이지)-1 ) * getPageSize(페이지크기)+1-1
        pageBean.setStartRow((pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + 1 - 1);

        // 결과가 존재하지 않을 시 예외처리
        model.addAttribute("isEmpty", page < 1 || page > pageBean.getPageCount());

        /*
            리스트맵에 리스트 출력에 필요한 각종정보 전달
            order_type = 정렬 타입 (new : 최근순, old : 오래된 순, like : 인기 순, hit : 조회순, comment : 댓글 순 )
         */
        ListMap.put("pageBean", pageBean);
        ListMap.put("order_type", order_type);

        /*
		VIEW 에 가져갈 객체 저장
		BoardBean boardBean = 게시판 정보
		List<PostBean> posts = 게시물 리스트
		PageBean pageBean = 페이지 빈
		 */
        model.addAttribute("boardBean", boardBean);
        model.addAttribute("posts", boardService.getPostList(ListMap));
        model.addAttribute("pageBean", pageBean);

        return StrResources.BOARD_PAGE;
    }

    /**
     * 게시물 작성 페이지
     *
     * @param session 세션
     * @param model   뷰에 전달할 객체
     * @param brd_key 게시판 KEY
     * @return "/page/form"
     */
    @RequestMapping(value = "/board/{brd_key}/write", method = RequestMethod.GET)
    public String write(HttpSession session, Model model, @PathVariable String brd_key) {
        System.out.println("BoardController - write() :: GET /board/" + brd_key + "/write");
        /*
        게시판 KEY 로 게시판 정보 호출
        존재하지 않을시 경고창 출력
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if (boardBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

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
        공지사항을 작성할 경우 권한 검증
         */
        if (boardBean.getBrd_key().equals("notice") && memberBean.getMem_is_admin() != 1) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
		VIEW 에 가져갈 객체 저장
		BoardBean boardBean = 게시판 정보
		 */
        model.addAttribute("boardBean", boardBean);
        return StrResources.BOARD_FORM_PAGE;
    }

    /**
     * 게시물 수정 페이지
     *
     * @param session 세션
     * @param model   모델
     * @param brd_key 게시판 KEY
     * @return "/page/form"
     */
    @RequestMapping(value = "/board/{brd_key}/update", method = RequestMethod.GET)
    public String update(HttpSession session, Model model, @PathVariable String brd_key,
                         @RequestParam(value = "post_id", required = false, defaultValue = "0") int post_id) {
        System.out.println("BoardController - update() :: GET /board/" + brd_key + "/update");

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
        게시물 PK 확인
         */
        if (post_id == 0) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시판 KEY 로 게시판 정보 호출
        존재하지 않을시 경고창 출력
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if (boardBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        공지사항을 수정할 경우 권한 검증
         */
        if (boardBean.getBrd_key().equals("notice") && memberBean.getMem_is_admin() != 1) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시물의 작성자인지 확인
         */
        PostBean postBean = boardService.getPost(post_id);
        if (postBean.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
		VIEW 에 가져갈 객체 저장
		BoardBean boardBean = 게시판 정보
		PostBean postBean = 게시물 정보
		 */
        model.addAttribute("boardBean", boardBean);
        model.addAttribute("postBean", postBean);
        return StrResources.BOARD_FORM_PAGE;
    }

    /**
     * 게시물 삭제
     *
     * @param session 세션
     * @param model   뷰에 전달할 객체
     * @param brd_key 게시판 KEY
     * @param post_id 게시물 PK
     * @return "/common/alertMessage"
     */
    @RequestMapping(value = "/board/{brd_key}/delete", method = RequestMethod.GET)
    public String delete(HttpSession session, Model model, @PathVariable String brd_key,
                         @RequestParam(value = "post_id", required = false, defaultValue = "0") int post_id) {
        System.out.println("BoardController - delete() :: GET /board/" + brd_key + "/delete");

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
        게시물 PK 확인
         */
        if (post_id == 0) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시판 KEY 로 게시판 정보 호출
        존재하지 않을시 경고창 출력
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if (boardBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        공지사항을 삭제 경우 권한 검증
         */
        if (boardBean.getBrd_key().equals("notice") && memberBean.getMem_is_admin() != 1) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시물 작성자인지 확인
         */
        PostBean postBean = boardService.getPost(post_id);
        if (postBean.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시물 삭제
         */
        int result = boardService.deletePost(post_id);
        if (result == 0) {
            model.addAttribute("msg", StrResources.FAIL_BOARD_DELETE);
        } else {
            model.addAttribute("msg", StrResources.SUCCESS_BOARD_DELETE);
            model.addAttribute("url", "/board/" + brd_key + "");
        }
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    /**
     * 이미지 업로드  product_edit페이지에서 맵핑되는 메소드
     *
     * @param request  요청
     * @param response 응답
     * @param upload   업로드한 이미지
     * @param session  세션
     * @throws Exception 예외처리
     */
    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
    public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload, HttpSession session)
    //MultipartFile 타입은 ckedit에서 upload란 이름으로 저장하게 된다
            throws Exception {

        MemberBean memberBean = (MemberBean) session.getAttribute("member");
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
        //String uploadPath = "resources/upload/";
        String uploadPath = "images/board/" + memberBean.getMem_userid() + "/";
//        프로젝트는 개발 디렉토리에 저장이 되는데 이미지를 업로드할 디렉토리를 개발 디렉토리로 설정하면 일일이 새로고침을 해주어야되서
//        불편하기 때문에 이미지를 업로드할 디렉토리를 배포 디렉토리로 설정한다.
        // System.out.println("디렉토리 : " + root_path + uploadPath);
        File Folder = new File(root_path + uploadPath);

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try {
                //폴더 생성합니다.
                if (Folder.mkdirs()) {
                    System.out.println("폴더가 생성되었습니다.");
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
        }

        OutputStream out = new FileOutputStream(root_path + uploadPath + fileName);

        // 서버로 업로드
        // write메소드의 매개값으로 파일의 총 바이트를 매개값으로 준다.
        // 지정된 바이트를 출력 스트립에 쓴다 (출력하기 위해서)
        out.write(bytes);

        // 클라이언트에 결과 표시
        String callback = request.getParameter("CKEditorFuncNum");
        System.out.println(callback);
        // 서버=>클라이언트로 텍스트 전송(자바스크립트 실행)
        PrintWriter printWriter = response.getWriter();

        // String fileUrl = request.getContextPath() + "/resources/upload/" + fileName;
        String fileUrl = request.getContextPath() + "/" + uploadPath + fileName;
        printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl
                + "','Image Upload Complete!')" + "</script>");
        printWriter.flush();
    }

    /**
     * 게시물 생성
     *
     * @param session  세션
     * @param request  요청
     * @param postBean 게시물빈
     * @param model    뷰에 전달할 객체
     * @param brd_key  게시판 KEY
     * @return "/common/alertMessage"
     */
    @RequestMapping(value = "/board/{brd_key}/write", method = RequestMethod.POST)
    public String write_post(HttpSession session, HttpServletRequest request, PostBean postBean, Model model, @PathVariable String brd_key) {
        System.out.println("BoardController - write_post() :: POST /board/" + brd_key + "/write");
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
        게시판 KEY 로 게시판 정보 호출
        존재하지 않을시 경고창 출력
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if (boardBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        공지사항을 작성할 경우 권한 검증
         */
        int isNotice = 0;
        if (boardBean.getBrd_key().equals("notice")) {
            isNotice++;
            if (memberBean.getMem_is_admin() != 1) {
                model.addAttribute("msg", StrResources.BAD_PERMISSION);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }

        /*
        글 제목이 비어있을 시 경고창 출력
         */
        if (postBean.getPost_title().trim().equals("")) {
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시물에 저장될 주요 정보들 세팅
         */
        postBean.setPost_device(postBean.getPost_device() != 0 ? postBean.getPost_device() : 1);
        postBean.setPost_ip(ScriptUtils.getIp(request));
        postBean.setBrd_id(boardBean.getBrd_id());
        postBean.setMem_id(memberBean.getMem_id());
        postBean.setPost_userid(memberBean.getMem_userid());
        postBean.setPost_nickname(memberBean.getMem_nickname());
        postBean.setPost_update_mem_id(memberBean.getMem_id());
        postBean.setPost_notice(isNotice);

        /*
        게시물 작성
         */
        int result = boardService.createPost(postBean);
        if (result == 1) {
            model.addAttribute("msg", StrResources.SUCCESS_BOARD_WRITE);
            model.addAttribute("url", "/board/" + brd_key + "");
        } else {
            model.addAttribute("msg", StrResources.FAIL_BOARD_WRITE);
        }
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    /**
     * 게시물 수정
     *
     * @param session  세션
     * @param model    뷰에 전달할 객체
     * @param brd_key  게시판 KEY
     * @param postBean 게시물 빈
     * @return "/common/alertMessage"
     */
    @RequestMapping(value = "/board/{brd_key}/update", method = RequestMethod.POST)
    public String update_post(HttpSession session, Model model, @PathVariable String brd_key, PostBean postBean) {
        System.out.println("BoardController - update() :: GET /board/" + brd_key + "/write");
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
        게시판 KEY 로 게시판 정보 호출
        존재하지 않을시 경고창 출력
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        if (boardBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        공지사항을 수정할 경우 권한 검증
         */
        if (boardBean.getBrd_key().equals("notice") && memberBean.getMem_is_admin() != 1) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시물 작성자인지 확인
         */
        PostBean postBean2 = boardService.getPost(postBean.getPost_id());
        if (postBean2.getMem_id() != memberBean.getMem_id()) {
            model.addAttribute("msg", StrResources.BAD_PERMISSION);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        /*
        게시물 수정
         */
        postBean.setPost_update_mem_id(memberBean.getMem_id());
        int result = boardService.updatePost(postBean);

        if (result == 0) {
            model.addAttribute("msg", StrResources.FAIL_BOARD_UPDATE);
        } else {
            model.addAttribute("msg", StrResources.SUCCESS_BOARD_UPDATE);
            model.addAttribute("url", "/board/" + brd_key + "/info/" + postBean.getPost_id());
        }
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    /**
     * 게시물 조회
     *
     * @param session  세션
     * @param request  요청
     * @param model    뷰에 전달할 객체
     * @param brd_key  게시판 KEY
     * @param post_id  게시물 PK
     * @param pageBean 페이지 빈
     * @param page     페이지
     * @return "/page/info"
     */
    @RequestMapping(value = "/board/{brd_key}/info/{post_id}", method = RequestMethod.GET)
    public String boardInfo(HttpSession session, HttpServletRequest request, Model model, @PathVariable String brd_key, @PathVariable int post_id, PageBean pageBean,
                            @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
        System.out.println("BoardController - boardInfo() :: GET /board/" + brd_key + "/info");

        /*
        게시판, 게시물 정보 호출
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        PostBean postBean = boardService.getPost(post_id);
        if (postBean == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        // 페이징 빈 설정
        pageBean.setCurrentPage(page);

        // 조회수 증가
        boardService.hitPost(post_id);

        // 게시물 좋아요, 싫어요 호출
        postBean.setPost_like(boardService.getLikeCount(post_id));
        postBean.setPost_dislike(boardService.getHateCount(post_id));
        boardService.setLikeCount(postBean);
        boardService.setHateCount(postBean);

        /*
        게시물 좋아요 싫어요 정보 호출
         */
        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put("member", session.getAttribute("member"));
        likeMap.put("post", postBean);
        likeMap.put("ip", ScriptUtils.getIp(request));

        LikedBean likedBean = boardService.getLiked(likeMap);

        /*
        게시물 작성자 경험치
         */
        memberService.setMemberPoint(postBean.getMem_id(), 1);

        /*
		VIEW 에 가져갈 객체 저장
		Map<String, Object> likedBean = 좋아요 정보
		BoardBean boardBean = 게시판 정보
		PostBean postBean = 게시물 리스트
		PageBean pageBean = 페이지 빈
		 */
        model.addAttribute("likedBean", likedBean);
        model.addAttribute("boardBean", boardBean);
        model.addAttribute("postBean", postBean);
        model.addAttribute("pageBean", pageBean);
        return StrResources.BOARD_INFO_PAGE;
    }

    /**
     * 추천 기능 (AJAX)
     *
     * @param request 요청
     * @param session 세션
     * @return ResponseEntity<String> AJAX
     */
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public ResponseEntity<String> like(HttpServletRequest request, HttpSession session) {
        System.out.println("BoardController - like() :: GET /like");
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        String result;
        ResponseEntity<String> entity;

        /*
        비로그인시 nologin 리턴
         */
        if (memberBean == null) {
            result = "nologin";
            try {
                entity = new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return entity;
        }

        /*
        게시물 정보 호출
         */
        int post_id = Integer.parseInt(request.getParameter("post_id"));
        PostBean postBean = boardService.getPost(post_id);

        /*
        likeMap에 기능에 필요한 각종 정보 저장
         */
        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put("member", memberBean);
        likeMap.put("post", postBean);
        likeMap.put("ip", ScriptUtils.getIp(request));
        try {
            LikedBean likedBean = boardService.getLiked(likeMap);
            likeMap.put("like", likedBean);
            if (likedBean != null) {
                if (likedBean.getLik_type() == 1) {
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
            entity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    /**
     * 비추천 기능 (AJAX)
     *
     * @param request 요청
     * @param session 세션
     * @return ResponseEntity<String> AJAX
     */
    @RequestMapping(value = "/hate", method = RequestMethod.GET)
    public ResponseEntity<String> hate(HttpServletRequest request, HttpSession session) {
        System.out.println("BoardController - hate() :: GET /hate");
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        String result;
        ResponseEntity<String> entity;

        /*
        비로그인시 nologin 리턴
         */
        if (memberBean == null) {
            result = "nologin";
            try {
                entity = new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return entity;
        }
        
        /*
        게시물 정보 호출
         */
        int post_id = Integer.parseInt(request.getParameter("post_id"));
        PostBean postBean = boardService.getPost(post_id);

        /*
        likeMap에 기능에 필요한 각종 정보 저장
         */
        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put("member", memberBean);
        likeMap.put("post", postBean);
        likeMap.put("ip", ScriptUtils.getIp(request));
        try {
            LikedBean likedBean = boardService.getLiked(likeMap);
            likeMap.put("like", likedBean);
            if (likedBean != null) {
                if (likedBean.getLik_type() == 2) {
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
            entity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 게시물 조회
    @RequestMapping(value = "/board/table", method = RequestMethod.GET)
    public String boardTable(Model model, @RequestParam(value = "brd_key", required = false) String brd_key) {
        System.out.println("BoardController - boardTable() :: GET /board/table");

        /*
        페이지 정보 1페이지 고정
         */
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(1); // 서블릿에 붙은 페이지를 저장
        pageBean.setPageNum("1");
        pageBean.setPageSize(5);
        
        /*
        리스트 출력에 필요한 각종 정보 저장
        삭제되지 않은 게시물, 게시판 PK, 최근순 정렬 고정
         */
        BoardBean boardBean = boardService.getBoardName(brd_key);
        Map<String, Object> ListMap = new HashMap<>();
        ListMap.put("isOnlyDel", 0);
        ListMap.put("brd_id", boardBean.getBrd_id());
        pageBean.setStartRow((pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + 1 - 1);
        ListMap.put("pageBean", pageBean);
        ListMap.put("order_type", "new");
        List<PostBean> posts = boardService.getPostList(ListMap);


        /*
		VIEW 에 가져갈 객체 저장
		String brd_name = 게시판 이름
		List<PostBean> posts = 게시물 리스트
		 */
        model.addAttribute("posts", posts);
        model.addAttribute("brd_name", boardBean.getBrd_name());
        model.addAttribute("brd_key", brd_key);
        return StrResources.PANEL_PAGE;
    }

}
