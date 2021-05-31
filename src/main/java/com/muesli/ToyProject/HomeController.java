package com.muesli.ToyProject;



import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.muesli.domain.*;
import com.muesli.service.BoardService;
import com.muesli.service.MenuService;
import com.muesli.util.StrResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Handles requests for the application home page.
 */

import com.muesli.service.MemberService;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	MemberService memberservice;

	@Inject
	MenuService menuService;

	@Inject
	BoardService boardService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("HomeController - home() :: GET /");
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String main(Model model, HttpSession session) {
		System.out.println("HomeController - main() :: GET /home");
		if(session.getAttribute("member") != null) {
			MemberBean memberBean = (MemberBean)session.getAttribute("member");
			MemberBean memberCheck = memberservice.getMember(memberBean);
			if(memberCheck.getMem_email_cert() == 0) {
				model.addAttribute("msg", StrResources.EMAIL_CERT);
				model.addAttribute("url", "/cert");
				return StrResources.ALERT_MESSAGE_PAGE;
			}
		}
		List<BoardBean> boardBeans = boardService.getBoardList();

		model.addAttribute("boards", boardBeans);
		return StrResources.MAIN_PAGE;
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(HttpSession session, Model model) {
		System.out.println("HomeController - top() :: GET /top");

		return StrResources.INCLUDE_TOP;
	}
	
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(Model model, HttpServletRequest request) {
		System.out.println("HomeController - header() :: GET /header");
		MemberBean admin = new MemberBean();
		admin.setMem_id(1);
		admin = memberservice.getMember(admin);
		if(admin.getMem_adminmemo().equals(StrResources.PAUSE)) {
			model.addAttribute("msg", StrResources.PAUSE);
			model.addAttribute("url", "/pause");
			return StrResources.ALERT_MESSAGE_PAGE;
		}



		return StrResources.INCLUDE_HEADER;
	}
	
	@RequestMapping(value = "/side", method = RequestMethod.GET)
	public String side(Model model, HttpServletRequest request) {
		System.out.println("HomeController - side() :: GET /side");
		// 메뉴 가져오기
		List<MenuBean> menuBeans = menuService.getMenuList();
		// 모델에 저장
		model.addAttribute("menus", menuBeans);
		return StrResources.INCLUDE_SIDE;
	}
	
	@RequestMapping(value = "/bottom", method = RequestMethod.GET)
	public String bottom() {
		System.out.println("HomeController - bottom() :: GET /bottom");
		return StrResources.INCLUDE_BOTTOM;
	}

	@RequestMapping(value = "/no-login", method = RequestMethod.GET)
	public String no_login(Model model) {
		System.out.println("HomeController - no_login() :: GET /no-login");
		model.addAttribute("msg", StrResources.LOGIN);
		model.addAttribute("url", "/login");
		return StrResources.ALERT_MESSAGE_PAGE;
	}

	@RequestMapping(value = "/no-permission", method = RequestMethod.GET)
	public String no_permission(Model model) {
		System.out.println("HomeController - no_permission() :: GET /no-permission");
		model.addAttribute("msg", StrResources.BAD_PERMISSION);
		return StrResources.ALERT_MESSAGE_PAGE;
	}

	@RequestMapping(value = "/pause", method = RequestMethod.GET)
	public String pause(Model model) {
		System.out.println("HomeController - pause() :: GET /pause");

		return StrResources.PAUSE_PAGE;
	}
}
