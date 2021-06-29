package com.muesli.service;

import javax.inject.Inject;

import com.muesli.domain.CurrentvisitorBean;
import com.muesli.domain.MemberLoginLogBean;
import org.springframework.stereotype.Service;

import com.muesli.dao.MemberDAO;
import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDAO memberDAO;

	@Override
	public int join(MemberBean memberBean) {
		System.out.println("MemberServiceImpl - join()");
		return memberDAO.insertMember(memberBean);
	}

	@Override
	public MemberBean getMember_userid(String mem_userid) {
		System.out.println("MemberServiceImpl - getMember_userid()");
		return memberDAO.getMember_userid(mem_userid);
	}

	@Override
	public MemberBean getMember_nickname(String mem_nickname) {
		System.out.println("MemberServiceImpl - getMember_nickname()");
		return memberDAO.getMember_nickname(mem_nickname);
	}

	@Override
	public MemberBean getMember(MemberBean memberBean) {
		System.out.println("MemberServiceImpl - getMember()");
		return memberDAO.getMember(memberBean);
	}

	@Override
	public int updateLoginMember(MemberBean memberCheck) {
		System.out.println("MemberServiceImpl - updateLoginMember()");
		return memberDAO.updateLoginMember(memberCheck);
	}

	@Override
	public int createMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberServiceImpl - createMemberEmailCode()");
		return memberDAO.createMemberEmailCode(memberAuthEmailBean);
	}

	@Override
	public int updateMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberServiceImpl - updateMemberEmailCode()");
		return memberDAO.updateMemberEmailCode(memberAuthEmailBean);

	}

	@Override
	public MemberAuthEmailBean getMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberServiceImpl - getMemberEmailCode()");
		return memberDAO.getMemberEmailCode(memberAuthEmailBean);

	}

	@Override
	public MemberAuthEmailBean checkMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberServiceImpl - checkMemberEmailCode()");
		return memberDAO.checkMemberEmailCode(memberAuthEmailBean);
	}

	@Override
	public int updateMemberEmailCert(MemberBean memberBean) {
		System.out.println("MemberServiceImpl - updateMemberEmailCert()");
		return memberDAO.updateMemberEmailCert(memberBean);
	}

	@Override
	public int useMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberServiceImpl - useMemberEmailCode()");
		return memberDAO.useMemberEmailCode(memberAuthEmailBean);
	}

	@Override
	public int getListCount(Map<String, Object> searchMap) {
		System.out.println("MemberServiceImpl - getListCount()");
		return memberDAO.getListCount(searchMap);
	}

	@Override
	public List<MemberBean> getMemberList(Map<String, Object> searchMap) {
		System.out.println("MemberServiceImpl - getMemberList()");
		return memberDAO.getMemberList(searchMap);
	}

	@Override
	public void insertLog(MemberLoginLogBean memberLoginLogBean) {
		System.out.println("MemberServiceImpl - insertLog()");
		memberDAO.insertLog(memberLoginLogBean);
	}

	@Override
	public void insertCurrentVisitor(CurrentvisitorBean currentvisitorBean) {
		System.out.println("MemberServiceImpl - insertCurrentVisitor()");
		memberDAO.insertCurrentVisitor(currentvisitorBean);
	}

	@Override
	public void deleteCurrentVisitor(CurrentvisitorBean currentvisitorBean) {
		System.out.println("MemberServiceImpl - insertLog()");
		memberDAO.deleteCurrentVisitor(currentvisitorBean);
	}

	@Override
	public void setMemberPoint(int mem_id, int point) {
		System.out.println("MemberServiceImpl - setMemberPoint()");
		MemberBean memberBean = new MemberBean();
		memberBean.setMem_id(mem_id);
		memberBean.setMem_point(point);
		memberDAO.setMemberPoint(memberBean);

		boolean isLevelUp = true;
		while (isLevelUp) {
			memberBean = getMember(memberBean);
			if (memberBean.getMem_point() > memberBean.getMem_level()*100) {
				memberBean.setMem_point(memberBean.getMem_point() - memberBean.getMem_level()*100);
				memberBean.setMem_level(memberBean.getMem_level() + 1);
				setMemberLevel(memberBean);
			} else {
				isLevelUp = false;
			}
		}
	}

	@Override
	public void setMemberLevel(MemberBean memberBean) {
		System.out.println("MemberServiceImpl - setMemberLevel()");
		memberDAO.setMemberLevel(memberBean);
	}

	@Override
	public int updateMemberPhoto(MemberBean memberBean) {
		System.out.println("MemberServiceImpl - updateMemberPhoto()");
		return memberDAO.updateMemberPhoto(memberBean);
	}
}
