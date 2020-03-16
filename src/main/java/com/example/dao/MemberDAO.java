package com.example.dao;

import java.util.List;

import com.example.dto.MemberVO;

public interface MemberDAO {
	
	public List<MemberVO> selectMember() throws Exception;
	
	public int registerMember(MemberVO member) throws Exception;
	
	public List<MemberVO> selectMember(MemberVO member) throws Exception;
	
	/* public MemberVO login(MemberVO member) throws Exception; */
}
