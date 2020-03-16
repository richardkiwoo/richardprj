package com.example.service;

import java.util.List;

import com.example.dto.MemberVO;

public interface MemberService {
	
	public List<MemberVO> selectMember() throws Exception;
	
	public int registerMember(MemberVO member) throws Exception;
	
	public int checkID(MemberVO member) throws Exception;
	
	public MemberVO login(MemberVO member) throws Exception;
}
