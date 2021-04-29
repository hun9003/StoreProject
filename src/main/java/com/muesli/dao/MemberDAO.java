package com.muesli.dao;

import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;

public interface MemberDAO {

	int insertMember(MemberBean memberBean);

	MemberBean getMember_userid(String mem_userid);

	MemberBean getMember_nickname(String mem_nickname);

	MemberBean getMember(MemberBean memberBean);

	int updateLoginMember(MemberBean memberCheck);

	int createMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

    int updateMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);

	MemberAuthEmailBean getMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean);
}
