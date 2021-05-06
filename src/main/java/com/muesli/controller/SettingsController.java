package com.muesli.controller;


import com.muesli.domain.MemberBean;
import com.muesli.domain.MenuBean;
import com.muesli.domain.PageBean;
import com.muesli.service.MemberService;
import com.muesli.service.MenuService;
import com.muesli.util.StrResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SettingsController {

    @Inject
    MemberService memberService;

    @Inject
    MenuService menuService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String main() {
        System.out.println("SettingsController - main() :: GET");

        return StrResources.SETTING_PAGE;
    }

    @RequestMapping(value = "/settings/top", method = RequestMethod.GET)
    public String top() {
        System.out.println("SettingsController - top() :: GET /settings/top");
        return StrResources.INCLUDE_SETTING_TOP;
    }

    @RequestMapping(value = "/settings/header", method = RequestMethod.GET)
    public String header(Model model, HttpServletRequest request) {
        System.out.println("SettingsController - header() :: GET /settings/header");
        return StrResources.INCLUDE_SETTING_HEADER;
    }

    @RequestMapping(value = "/settings/side", method = RequestMethod.GET)
    public String side(Model model, HttpServletRequest request) {
        System.out.println("SettingsController - side() :: GET /settings/side");
        return StrResources.INCLUDE_SETTING_SIDE;
    }

    @RequestMapping(value = "/settings/bottom", method = RequestMethod.GET)
    public String bottom() {
        System.out.println("SettingsController - bottom() :: GET /settings/header");
        return StrResources.INCLUDE_SETTING_BOTTOM;
    }

    // 회원 관리 페이지
    @RequestMapping(value = "/settings/members", method = RequestMethod.GET)
    public String members() {
        System.out.println("SettingsController - bottom() :: GET /settings/members");
        // 페이징 하지 않은 서블릿은 강제로 1을 붙여서 다시 리다이렉트
        return StrResources.REDIRECT+"/settings/members/1";
    }

    // 회원 관리 페이지 (페이징)
    @RequestMapping(value = "/settings/members/{page}", method = RequestMethod.GET)
    public String members(@PathVariable int page, HttpServletRequest request, Model model) {
        System.out.println("SettingsController - members() :: GET /settings/members/" + page);

        // 페이지 제목
        String subTitle = "회원목록";
        // 차단한 멤버 확인 여부 체크
        int deniedType;
        if (request.getParameter("mem_denied") != null) {
            deniedType = Integer.parseInt(request.getParameter("mem_denied"));
        } else {
            deniedType = 0;
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

        // 맵 객체에 페이지정보와 검색타입과 내용 저장
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("pageBean", pageBean);

        if (request.getParameter("sch_type") != null) {
            searchMap.put("sch_type", request.getParameter("sch_type"));
            searchMap.put("sch_content", request.getParameter("sch_content"));
        }

        // 차단 회원 표시 여부 저장
        searchMap.put("denied", deniedType);

        // 페이지빈에 리스트의 총 갯수를 저장
        pageBean.setCount(memberService.getListCount(searchMap));
        pageBean.setStartRow((pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + 1 - 1);

        // 페이지가 존재하지않는 곳을 리다이렉트 했을때 강제로 1페이지 이동처리
        if (page < 1 || page > pageBean.getPageCount()) {
            model.addAttribute("msg", StrResources.RESULT_EMPTY);
            model.addAttribute("url", "/settings/members/1");
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        List<MemberBean> memberBeanList = memberService.getMemberList(searchMap);

        // 뷰에 가져갈 모델에 리스트와 페이지와 각종 정보 저장
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("members", memberBeanList);
        model.addAttribute("subTitle", subTitle);
        return StrResources.SETTING_MEMBER_PAGE;
    }

    // 회원 정보 조회
    @RequestMapping(value = "/settings/members/info/{mem_id}", method = RequestMethod.GET)
    public String member_info(@PathVariable int mem_id, Model model) {
        System.out.println("SettingsController - member_info() :: GET /settings/members/info/" + mem_id);

        // 페이지 제목
        String subTitle = "회원정보";

        // 멤버 PK로 정보 호출
        MemberBean memberBean = new MemberBean();
        memberBean.setMem_id(mem_id);
        MemberBean memberInfo = memberService.getMember(memberBean);

        // 모델에 저장
        model.addAttribute("subTitle", subTitle);
        model.addAttribute("memberInfo", memberInfo);
        return StrResources.SETTING_MEMBER_INFO;
    }

    // 메뉴 그룹 관리
    @RequestMapping(value = "/settings/menu-group", method = RequestMethod.GET)
    public String menu_group(Model model) {
        System.out.println("SettingsController - menu_group() :: GET /settings/menu-group");
        // 서브타이틀과 메뉴리스트 담기
        String subTitle = "메뉴 그룹";
        List<MenuBean> menuBeans = menuService.getMenuList();

        // 모델에 저장
        model.addAttribute("isMenuGroup", true);
        model.addAttribute("menus", menuBeans);
        model.addAttribute("subTitle", subTitle);
        return StrResources.SETTING_MENU_PAGE;
    }

    // 메뉴 관리
    @RequestMapping(value = "/settings/menu", method = RequestMethod.GET)
    public String menu(Model model, HttpServletRequest request) {
        System.out.println("SettingsController - menu() :: GET /settings/menu");
        // 서브타이틀과 메뉴가 속한 그룹의 PK 받아오기
        String subTitle = "메뉴";
        String menuParent = request.getParameter("menuParent");

        // 만약 메뉴가 속한 그룹이 널스트링으로 저장되었을 시 널로 처리
        if(menuParent != null && menuParent.equals("")){
            menuParent = null;
        }
        // 메뉴리스트 받아오기
        List<MenuBean> menuBeans = menuService.getMenuList();

        // 모델에 저장
        model.addAttribute("menuParent", menuParent);
        model.addAttribute("isMenuGroup", false);
        model.addAttribute("menus", menuBeans);
        model.addAttribute("subTitle", subTitle);
        return StrResources.SETTING_MENU_PAGE;
    }

    // 메뉴 정보 조회
    @RequestMapping(value = "/settings/menu-info/{men_id}", method = RequestMethod.GET)
    public String menu_info(Model model, @PathVariable int men_id) {
        System.out.println("SettingsController - menu_info() :: GET /settings/menu-info/" + men_id);

        // 메뉴 PK로 정보 가져와서 저장
        MenuBean menuBean = menuService.getMenu(men_id);
        model.addAttribute("menu", menuBean);

        return StrResources.SETTING_MENU_INFO;
    }

    // 메뉴 정보 수정
    @RequestMapping(value = "/settings/menu-info/{men_id}", method = RequestMethod.POST)
    public String menu_info_post(Model model, @PathVariable int men_id, MenuBean menuBean, HttpServletRequest request) {
        System.out.println("SettingsController - menu_info_post() :: POST /settings/menu-info/" + men_id);

        // 새창으로 열기가 널일시 기본값으로 _self 넣기
        if (request.getParameter("men_target") == null) {
            menuBean.setMen_target("_self");
        }

        // 메뉴 수정하기
        int isSuccess = menuService.updateMenu(menuBean);

        // 리다이렉트 주소랑 메뉴 그룹 PK 담아서 매핑하기
        String url;
        String menuParent = "";
        if(menuBean.getMen_parent() != null){
            menuParent = "?menuParent="+menuBean.getMen_parent();
        }

        // 매핑될 주소
        if (menuBean.getMen_parent() != null) {
            url = "/settings/menu"+menuParent;
        } else {
            url = "/settings/menu-group";
        }

        // 성공, 실패 메세지 저장하기
        if (isSuccess == 1) {
            model.addAttribute("msg", StrResources.SUCCESS);
            model.addAttribute("url", url);
        } else {
            model.addAttribute("msg", StrResources.FAIL);
        }

        return StrResources.ALERT_MESSAGE_PAGE;
    }

    // 메뉴 순서 수정
    @RequestMapping(value = "/settings/menu-order", method = RequestMethod.GET)
    public String menu_order(Model model) {
        System.out.println("SettingsController - menu_order() :: GET /settings/menu-order");

        // 잘못 들어온 리다이렉트 처리하기
        model.addAttribute("msg", StrResources.BAD_REDIRECT);
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    @RequestMapping(value = "/settings/menu-order/{men_id}", method = RequestMethod.GET)
    public String menu_order(Model model, @PathVariable int men_id, HttpServletRequest request) {
        System.out.println("SettingsController - menu_order() :: GET /settings/menu-order/" + men_id);
        String url = "menu";
        // 잘못된 접근 방지
        if (request.getParameter("orderType") == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        boolean isUp;

        // 순서 타입 판별
        if (request.getParameter("orderType").equals("up")) {
            isUp = true;
        } else if (request.getParameter("orderType").equals("down")) {
            isUp = false;
        } else {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        // 메뉴 그룹여부 판별
        boolean isMenuGroup = false;
        MenuBean menuBean = menuService.getMenu(men_id);
        if (menuBean.getMen_parent() == null) {
            isMenuGroup = true;
            url = "menu-group";
        }

        // 총 갯수 호출
        int maxOrder;
        if (isMenuGroup) {
            maxOrder = menuService.getMenuGroupCount();
        } else {
            maxOrder = menuService.getMenuCount(menuBean.getMen_parent());
        }

        // 메뉴 순서를 더이상 변경할 수 없을 때
        if(isUp){
            if (menuBean.getMen_order() <= 1) {
                model.addAttribute("msg", StrResources.FAIL_UPDATE_ORDER);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        } else {
            if (menuBean.getMen_order() >= maxOrder) {
                model.addAttribute("msg", StrResources.FAIL_UPDATE_ORDER);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        }

        // 순서변경을 위해 필요한 정보 Map객체에 담기
        Map<String, Object> menuMap = new HashMap<String, Object>();
        menuMap.put("menuBean", menuBean);
        menuMap.put("isUp", isUp);
        menuMap.put("isMenuGroup", isMenuGroup);

        // 대상 메뉴 순서 변경
        int result = menuService.updateOtherOrder(menuMap);
        if(result == 0){
            model.addAttribute("msg", StrResources.ERROR);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        // 해당 메뉴 순서 변경
        result = menuService.updateOrder(menuMap);
        if(result == 0){
        	model.addAttribute("msg", StrResources.ERROR);
            return StrResources.ALERT_MESSAGE_PAGE;
		}

        // 메뉴를 수정했을때 그룹의 PK 담아보내기
        String menuParent = "";
        if(menuBean.getMen_parent() != null){
            menuParent = "?menuParent="+menuBean.getMen_parent();
        }

        return StrResources.REDIRECT+"/settings/"+url+menuParent;
    }

    // 메뉴 생성
    @RequestMapping(value = "/settings/menu-create", method = RequestMethod.GET)
    public String menu_create(Model model, HttpServletRequest request) {
        System.out.println("SettingsController - menu_create() :: GET /settings/menu-create");

        // 그룹인지 메뉴인지 저장후 메뉴라면 메뉴그룹 PK 정보 저장
        String menuType = request.getParameter("menuType");
        if(request.getParameter("menuParent") != null){
            model.addAttribute("menuParent", request.getParameter("menuParent"));
        }

        // 서브타이틀 저장
        String menuTitle = "";
        if(menuType != null){
            switch (menuType){
                case "men": menuTitle = "메뉴 생성";break;
                case "meg": menuTitle = "메뉴 그룹 생성";break;
            }
        } else {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        model.addAttribute("menuTitle", menuTitle);
        model.addAttribute("menuType", menuType);

        return StrResources.SETTING_MENU_INFO;
    }

    @RequestMapping(value = "/settings/menu-create", method = RequestMethod.POST)
    public String menu_create_post(Model model, HttpServletRequest request, MenuBean menuBean) {
        System.out.println("SettingsController - menu_create_post() :: POST /settings/menu-create");

        // 그룹인지 메뉴인지 판별해서 저장
        String menuType = "";
        String url = "";
        if(menuBean.getMen_parent() != null){
            menuType = "men";
            url = "menu";
        } else {
            menuType = "meg";
            url = "menu-group";
        }

        // 새창으로열기 널값일때 기본값 저장
        if (request.getParameter("men_target") == null) {
            menuBean.setMen_target("_self");
        }

        // 각 메뉴의 갯수를 구한뒤 +1 해서 order 저장해주기
        switch (menuType) {
            case "men" : menuBean.setMen_order(menuService.getMenuCount(menuBean.getMen_parent())+1);break;
            case "meg" : menuBean.setMen_order(menuService.getMenuGroupCount()+1);break;
        }

        // 메뉴 생성
        int result = menuService.insertMenu(menuBean);
        if(result < 1) {
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        // 메뉴 그룹 PK 저장
        String menuParent = "";
        if(menuBean.getMen_parent() != null){
            menuParent = "?menuParent="+menuBean.getMen_parent();
        }

        return StrResources.REDIRECT+"/settings/"+url+menuParent;
    }

    @RequestMapping(value = "/settings/menu-delete", method = RequestMethod.GET)
    public String menu_delete(Model model, HttpServletRequest request) {
        System.out.println("SettingsController - menu_delete() :: GET /settings/menu-delete");
        // 삭제할 PK 저장
        int men_id = 0;
        if(request.getParameter("men_id") != null){
            men_id = Integer.parseInt(request.getParameter("men_id"));
        }
        // 삭제할 메뉴 정보 호출
        MenuBean menuBean = menuService.getMenu(men_id);

        // 메뉴를 삭제했을시 그룹 PK 저장
        String url = "";
        String menuParent = "";
        if(menuBean.getMen_parent() != null){
            url = "menu";
            menuParent = "?menuParent="+menuBean.getMen_parent();
        } else {
            url = "menu-group";
        }

        int result = menuService.deleteMenu(men_id);
        if(result < 1) {
            model.addAttribute("msg", StrResources.FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        // 삭제에 성공 했을때 삭제 메뉴 뒷번호들 한번호씩 땡기기
        menuService.pushMenuOrder(menuBean);

        return StrResources.REDIRECT+"/settings/"+url+menuParent;
    }
}
