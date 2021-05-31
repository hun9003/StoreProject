package com.muesli.util;

import com.muesli.domain.CurrentvisitorBean;
import com.muesli.domain.MemberBean;
import com.muesli.service.MemberService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Timestamp;



public class CountManager implements HttpSessionListener {
    public static int count;

    @Inject
    MemberService memberService;

    public static int getCount() {
        return count;
    }

    public void sessionCreated(HttpSessionEvent event) {
        //세션이 만들어질 때 호출
        HttpSession session = event.getSession(); //request에서 얻는 session과 동일한 객체
        session.setMaxInactiveInterval(60*20);

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());

        MemberBean memberBean = (MemberBean)session.getAttribute("member");
        CurrentvisitorBean currentvisitorBean = new CurrentvisitorBean();
        currentvisitorBean.setCur_ip(memberBean.getMem_lastlogin_ip());
        currentvisitorBean.setMem_id(memberBean.getMem_id());
        currentvisitorBean.setCur_mem_name(memberBean.getMem_nickname());
        currentvisitorBean.setCur_datetime(nowTime);
        currentvisitorBean.setCur_page("");
        currentvisitorBean.setCur_url("");
        currentvisitorBean.setCur_referer("");
        currentvisitorBean.setCur_useragent("");
        memberService.insertCurrentVisitor(currentvisitorBean);

        count++;

        session.getServletContext().log(session.getId() + " 세션생성 " + ", 접속자수 : " + count);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        //세션이 소멸될 때 호출
        count--;
        if(count<0)
            count=0;

        HttpSession session = event.getSession();

        MemberBean memberBean = (MemberBean)session.getAttribute("member");
        CurrentvisitorBean currentvisitorBean = new CurrentvisitorBean();
        currentvisitorBean.setCur_ip(memberBean.getMem_lastlogin_ip());
        memberService.deleteCurrentVisitor(currentvisitorBean);

        session.getServletContext().log(session.getId() + " 세션소멸 " + ", 접속자수 : " + count);
    }


}

