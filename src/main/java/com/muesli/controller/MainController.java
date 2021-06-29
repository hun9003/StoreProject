package com.muesli.controller;

import com.muesli.util.StrResources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 메인 페이지 컨트롤러 클래스
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
public class MainController {

	/**
	 * 메인 페이지
	 * @return "/home"
	 */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        System.out.println("MainController - main() :: GET");

        return StrResources.MAIN_PAGE;
    }
}
