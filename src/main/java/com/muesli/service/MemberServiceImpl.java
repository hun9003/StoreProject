package com.muesli.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.muesli.dao.MemberDAO;
import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;

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
}
