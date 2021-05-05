package com.muesli.ToyProject;



import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.muesli.util.StrResources;
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
				model.addAttribute("msg", StrResources.EMAIL_CERT);
				model.addAttribute("url", "cert");
				return StrResources.ALERT_MESSAGE_PAGE;
			}
		}
		return StrResources.MAIN_PAGE;
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top() {
		System.out.println("HomeController - top() :: GET /top");
		return StrResources.INCLUDE_TOP;
	}
	
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(Model model, HttpServletRequest request) {
		System.out.println("HomeController - header() :: GET /header");
		return StrResources.INCLUDE_SETTING_HEADER;
	}
	
	@RequestMapping(value = "/side", method = RequestMethod.GET)
	public String side(Model model, HttpServletRequest request) {
		System.out.println("HomeController - side() :: GET /side");
		return StrResources.INCLUDE_SETTING_SIDE;
	}
	
	@RequestMapping(value = "/bottom", method = RequestMethod.GET)
	public String bottom() {
		System.out.println("HomeController - bottom() :: GET /header");
		return StrResources.INCLUDE_SETTING_BOTTOM;
	}
}
