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
        System.out.println("HomeController - top() :: GET /settings/top");
        return StrResources.INCLUDE_SETTING_TOP;
    }

    @RequestMapping(value = "/settings/header", method = RequestMethod.GET)
    public String header(Model model, HttpServletRequest request) {
        System.out.println("HomeController - header() :: GET /settings/header");
        return StrResources.INCLUDE_SETTING_HEADER;
    }

    @RequestMapping(value = "/settings/side", method = RequestMethod.GET)
    public String side(Model model, HttpServletRequest request) {
        System.out.println("HomeController - side() :: GET /settings/side");
        return StrResources.INCLUDE_SETTING_SIDE;
    }

    @RequestMapping(value = "/settings/bottom", method = RequestMethod.GET)
    public String bottom() {
        System.out.println("HomeController - bottom() :: GET /settings/header");
        return StrResources.INCLUDE_SETTING_BOTTOM;
    }

    // 회원 관리 페이지
    @RequestMapping(value = "/settings/members", method = RequestMethod.GET)
    public String members() {
        System.out.println("HomeController - bottom() :: GET /settings/members");
        // 페이징 하지 않은 서블릿은 강제로 1을 붙여서 다시 리다이렉트
        return StrResources.REDIRECT+"/settings/members/1";
    }

    // 회원 관리 페이지 (페이징)
    @RequestMapping(value = "/settings/members/{page}", method = RequestMethod.GET)
    public String members(@PathVariable int page, HttpServletRequest request, Model model) {
        System.out.println("HomeController - members() :: GET /settings/members/" + page);

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
        System.out.println("HomeController - member_info() :: GET /settings/members/info/" + mem_id);

        // 페이지 제목
        String subTitle = "회원정보";

        MemberBean memberBean = new MemberBean();
        memberBean.setMem_id(mem_id);
        MemberBean memberInfo = memberService.getMember(memberBean);

        model.addAttribute("subTitle", subTitle);
        model.addAttribute("memberInfo", memberInfo);
        return StrResources.SETTING_MEMBER_INFO;
    }

    // 메뉴 그룹 관리
    @RequestMapping(value = "/settings/menu-group", method = RequestMethod.GET)
    public String menu(Model model) {
        System.out.println("HomeController - menu() :: GET /settings/menu-group");
        String subTitle = "메뉴그룹관리";

        List<MenuBean> menuBeans = menuService.getMenuList();

        model.addAttribute("menus", menuBeans);
        model.addAttribute("subTitle", subTitle);
        return StrResources.SETTING_MENU_PAGE;
    }

    // 메뉴 정보 조회
    @RequestMapping(value = "/settings/menu-info/{men_id}", method = RequestMethod.GET)
    public String menu_info(Model model, @PathVariable int men_id) {
        System.out.println("HomeController - menu_info() :: GET /settings/menu-info/" + men_id);

        MenuBean menuBean = menuService.getMenu(men_id);
        model.addAttribute("menu", menuBean);

        return StrResources.SETTING_MEMBER_INFO;
    }

    // 메뉴 정보 수정
    @RequestMapping(value = "/settings/menu-info/{men_id}", method = RequestMethod.POST)
    public String menu_info_post(Model model, @PathVariable int men_id, MenuBean menuBean, HttpServletRequest request) {
        System.out.println("HomeController - menu_info_post() :: POST /settings/menu-info/" + men_id);

        if (request.getParameter("men_target") == null) {
            menuBean.setMen_target("_self");
        }

        int isSuccess = menuService.updateMenu(menuBean);
        String url;


        if (menuBean.getMen_parent() != null) {
            url = "/settings/menu";
        } else {
            url = "/settings/menu-group";
        }
        if (isSuccess == 1) {
            model.addAttribute("msg", StrResources.SUCCESS);
            model.addAttribute("url", url);
        } else {
            model.addAttribute("msg", StrResources.FAIL);
        }
        return StrResources.ALERT_MESSAGE_PAGE;
    }

    // 메뉴 정보 수정
    @RequestMapping(value = "/settings/menu-order", method = RequestMethod.GET)
    public String menu_order(Model model) {
        System.out.println("HomeController - menu_order() :: GET /settings/menu-order");

        model.addAttribute("msg", StrResources.BAD_REDIRECT);
        return StrResources.ALERT_MESSAGE_PAGE;
    }
    @RequestMapping(value = "/settings/menu-order/{men_id}", method = RequestMethod.GET)
    public String menu_order(Model model, @PathVariable int men_id, HttpServletRequest request) {
        System.out.println("HomeController - menu_order() :: GET /settings/menu-order/" + men_id);

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
        return StrResources.REDIRECT+"/settings/menu-group";
    }
}
