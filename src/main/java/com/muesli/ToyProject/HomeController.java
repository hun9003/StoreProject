package com.muesli.ToyProject;



import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Handles requests for the application home page.
 */

import com.muesli.domain.MemberBean;
import com.muesli.service.MemberService;
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	MemberService memberservice;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("HomeController - home() :: GET /");
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String main(HttpSession session, Model model) {
		System.out.println("HomeController - main() :: GET /home");
		if(session.getAttribute("member") != null) {
			MemberBean memberBean = (MemberBean)session.getAttribute("member");
			MemberBean memberCheck = memberservice.getMember(memberBean);
			if(memberCheck.getMem_email_cert() == 0) {
				model.addAttribute("msg", "이메일 인증 미완료 회원입니다. 이메일 인증을 진행해주세요."); 
				model.addAttribute("url", "cert");
				return "/common/alertMessage";
			}
		}
		return "/home";
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top() {
		System.out.println("HomeController - top() :: GET /top");
		return "/include/top";
	}
	
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(Model model, HttpServletRequest request) {
		System.out.println("HomeController - header() :: GET /header");
		return "/include/header";
	}
	
	@RequestMapping(value = "/side", method = RequestMethod.GET)
	public String side(Model model, HttpServletRequest request) {
		System.out.println("HomeController - side() :: GET /side");
		return "/include/side";
	}
	
	@RequestMapping(value = "/bottom", method = RequestMethod.GET)
	public String bottom() {
		System.out.println("HomeController - bottom() :: GET /header");
		return "/include/bottom";
	}
}
