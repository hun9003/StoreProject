package com.muesli.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "com.muesli.mapper.MemberMapper";
	
	// 회원가입
	@Override
	public int insertMember(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - insertMember()");
		return sqlSession.insert(namespace+".insertMember", memberBean);
	}
	
	// 아이디 중복 체크
	@Override
	public MemberBean getMember_userid(String mem_userid) {
		System.out.println("MemberDAOImpl - getMember_userid()");
		return sqlSession.selectOne(namespace+".getMember_userid", mem_userid);
	}

	// 닉네임 중복 체크
	@Override
	public MemberBean getMember_nickname(String mem_nickname) {
		System.out.println("MemberDAOImpl - getMember_nickname()");
		return sqlSession.selectOne(namespace+".getMember_nickname", mem_nickname);
	}
	
	// 회원 정보 가져오기
	@Override
	public MemberBean getMember(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - getMember()");
		return sqlSession.selectOne(namespace+".getMember", memberBean);
	}
	
	// 로그인 기록 수정
	@Override
	public int updateLoginMember(MemberBean memberCheck) {
		System.out.println("MemberDAOImpl - updateLoginMember()");
		return sqlSession.update(namespace+".updateLoginMember", memberCheck);
	}
	
	// 메일 코드 정보 삽입
	@Override
	public int createMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - createMemberEmailCode()");
		return sqlSession.insert(namespace+".createMemberEmailCode", memberAuthEmailBean);
	}

	@Override
	public int updateMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - updateMemberEmailCode()");
		return sqlSession.update(namespace+".updateMemberEmailCode", memberAuthEmailBean);
	}

	@Override
	public MemberAuthEmailBean getMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - getMemberEmailCode()");
		return sqlSession.selectOne(namespace+".getMemberEmailCode", memberAuthEmailBean);
	}

	@Override
	public MemberAuthEmailBean checkMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - checkMemberEmailCode()");
		return sqlSession.selectOne(namespace+".checkMemberEmailCode", memberAuthEmailBean);
	}

	@Override
	public int updateMemberEmailCert(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - updateMemberEmailCert()");
		return sqlSession.update(namespace+".updateMemberEmailCert", memberBean);
	}

	@Override
	public int useMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - useMemberEmailCode()");
		return sqlSession.update(namespace+".useMemberEmailCode", memberAuthEmailBean);
	}


}