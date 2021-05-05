package com.muesli.service;

import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;

import java.util.List;
import java.util.Map;

public interface MemberService {

	int join(MemberBean memberBean);

	MemberBean getMember_userid(String mem_userid);

	MemberBean getMember_nickname(String mem_nickname);

	MemberBean getMember(MemberBean memberBean);

	int updateLoginMember(MemberBean memberCheck);

	int createMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

    int updateMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

	MemberAuthEmailBean getMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

    MemberAuthEmailBean checkMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

	int updateMemberEmailCert(MemberBean memberBean);

    int useMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

    int getListCount(Map<String, Object> searchMap);

	List<MemberBean> getMemberList(Map<String, Object> searchMap);
}
