package com.example.service;

public interface SnsLoginService {
	public String sns_url_naver() throws Exception;
	public String sns_url_kakao() throws Exception;
	public String sns_url_google() throws Exception;
	
	public int snsLogin(String code) throws Exception;
}
