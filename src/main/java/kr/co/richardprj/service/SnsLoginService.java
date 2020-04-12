package kr.co.richardprj.service;

import com.example.dto.MemberVO;

import kr.co.richardprj.swp.auth.SnsValue;

public interface SnsLoginService {
	
	public MemberVO getUserProfileKakao(String code) throws Exception;
	public MemberVO getSnsUserProfile(SnsValue sns,String code) throws Exception;
	public String getSnsUrls(SnsValue sns) throws Exception;
	
	public int snsLogin(String code) throws Exception;
	
}
