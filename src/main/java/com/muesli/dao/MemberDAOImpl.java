package com.muesli.dao;

import javax.inject.Inject;

import com.muesli.domain.CurrentvisitorBean;
import com.muesli.domain.MemberLoginLogBean;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;

import java.util.List;
import java.util.Map;

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

	// 메일 코드 재전송
	@Override
	public int updateMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - updateMemberEmailCode()");
		return sqlSession.update(namespace+".updateMemberEmailCode", memberAuthEmailBean);
	}

	// 메일 코드 가져오기
	@Override
	public MemberAuthEmailBean getMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - getMemberEmailCode()");
		return sqlSession.selectOne(namespace+".getMemberEmailCode", memberAuthEmailBean);
	}

	// 메일 코드 체크
	@Override
	public MemberAuthEmailBean checkMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - checkMemberEmailCode()");
		return sqlSession.selectOne(namespace+".checkMemberEmailCode", memberAuthEmailBean);
	}

	// 회원 메일 인증 여부 변경
	@Override
	public int updateMemberEmailCert(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - updateMemberEmailCert()");
		return sqlSession.update(namespace+".updateMemberEmailCert", memberBean);
	}

	// 메일 사용 여부 변경
	@Override
	public int useMemberEmailCode(MemberAuthEmailBean memberAuthEmailBean) {
		System.out.println("MemberDAOImpl - useMemberEmailCode()");
		return sqlSession.update(namespace+".useMemberEmailCode", memberAuthEmailBean);
	}

	// 회원 리스트 갯수 가져오기
	@Override
	public int getListCount(Map<String, Object> searchMap) {
		System.out.println("MemberDAOImpl - getListCount()");
		return sqlSession.selectOne(namespace+".getListCount", searchMap);
	}

	// 회원 리스트 가져오기
	@Override
	public List<MemberBean> getMemberList(Map<String, Object> searchMap) {
		System.out.println("MemberDAOImpl - getMemberList()");
		return sqlSession.selectList(namespace+".getMemberList", searchMap);
	}

	@Override
	public void insertLog(MemberLoginLogBean memberLoginLogBean) {
		System.out.println("MemberDAOImpl - insertLog()");
		sqlSession.insert(namespace+".insertLog", memberLoginLogBean);
	}

	@Override
	public void insertCurrentVisitor(CurrentvisitorBean currentvisitorBean) {
		System.out.println("MemberDAOImpl - insertCurrentVisitor()");
		sqlSession.insert(namespace+".insertCurrentVisitor", currentvisitorBean);
	}

	@Override
	public void deleteCurrentVisitor(CurrentvisitorBean currentvisitorBean) {
		System.out.println("MemberDAOImpl - deleteCurrentVisitor()");
		sqlSession.delete(namespace+".deleteCurrentVisitor", currentvisitorBean);
	}

	@Override
	public void setMemberPoint(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - setMemberPoint()");
		sqlSession.update(namespace+".setMemberPoint", memberBean);
	}

	@Override
	public void setMemberLevel(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - setMemberLevel()");
		sqlSession.update(namespace+".setMemberLevel", memberBean);
	}

	@Override
	public int updateMemberPhoto(MemberBean memberBean) {
		System.out.println("MemberDAOImpl - updateMemberPhoto()");
		return sqlSession.update(namespace+".updateMemberPhoto", memberBean);
	}


}
