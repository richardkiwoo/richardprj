package com.example.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.dao.MemberDAO;
import com.example.dto.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	private MemberDAO dao;
	
	@Override
	public List<MemberVO> selectMember() throws Exception {

		return dao.selectMember();
	}

	@Override
	public int registerMember(MemberVO member) throws Exception {
		return dao.registerMember(member);
	}

	@Override
	public int checkID(MemberVO member) throws Exception {
		List<MemberVO> members = dao.selectMember(member);
		
		return members.size();
	}

	@Override
	public MemberVO login(MemberVO member) throws Exception {
		//MemberVO mbr = dao.login(member);
		List<MemberVO> members = dao.selectMember(member);
		MemberVO retMember = null;
		
		if ( members.size() > 0 )
			retMember = members.get(0);

		return retMember;
	}


}
