package com.example.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.dto.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.example.mapper.memberMapper";
	
	@Override
	public List<MemberVO> selectMember() throws Exception {

		return sqlSession.selectList(Namespace+".selectMember");
	}

	@Override
	public int registerMember(MemberVO member) throws Exception {
		int result = sqlSession.insert(Namespace+".registerMember",member);
		return result;
	}

	@Override
	public List<MemberVO> selectMember(MemberVO member) throws Exception {
		return sqlSession.selectList(Namespace+".selectMembers", member);
	}

	/*
	 * @Override public MemberVO login(MemberVO member) throws Exception { return
	 * (MemberVO) sqlSession.selectMap(Namespace+".loginMember", member.getMbrid());
	 * }
	 */
	
}
