package kr.co.richardprj.swp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;


public class SnsValue2 implements SnsUrls{
	
	@Autowired
	ApplicationContext context;
	
	final String naver_client_id ;
	final String naver_client_secret ;
	final String naver_access_token_url;
	final String naver_redirect_url ;
	
	final String kakao_client_id ;
	final String kakao_client_secret ;
	final String kakao_access_token_url ;
	final String kakao_redirect_url ;
	
	final String google_client_id ;
	final String google_client_secret ;
	final String google_access_token_url ;
	final String google_redirect_url ;
	
	
	
	public SnsValue2() {
		Environment env = context.getEnvironment();
		
		this.naver_client_id = env.getProperty("oauth.naver.client.id");
		this.naver_client_secret = env.getProperty("oauth.naver.client.secret");
		this.naver_access_token_url = env.getProperty("oauth.naver.accesstokenurl");
		this.naver_redirect_url = env.getProperty("oauth.naver.redirecturl");
		
		
		this.kakao_client_id = env.getProperty("oauth.kakao.client.id");
		this.kakao_client_secret = env.getProperty("oauth.kakao.client.secret");
		this.kakao_access_token_url = env.getProperty("oauth.kakao.accesstokenurl");
		this.kakao_redirect_url = env.getProperty("oauth.kakao.redirecturl");
		
		this.google_client_id = env.getProperty("oauth.google.client.id");
		this.google_client_secret = env.getProperty("oauth.google.client.secret");
		this.google_access_token_url = env.getProperty("oauth.google.accesstokenurl");
		this.google_redirect_url = env.getProperty("oauth.google.redirecturl");
		
	}


	public ApplicationContext getContext() {
		return context;
	}


	public void setContext(ApplicationContext context) {
		this.context = context;
	}


	public String getNaver_client_id() {
		return naver_client_id;
	}


	public String getNaver_client_secret() {
		return naver_client_secret;
	}


	public String getNaver_access_token_url() {
		return naver_access_token_url;
	}


	public String getNaver_redirect_url() {
		return naver_redirect_url;
	}


	public String getKakao_client_id() {
		return kakao_client_id;
	}


	public String getKakao_client_secret() {
		return kakao_client_secret;
	}


	public String getKakao_access_token_url() {
		return kakao_access_token_url;
	}


	public String getKakao_redirect_url() {
		return kakao_redirect_url;
	}


	public String getGoogle_client_id() {
		return google_client_id;
	}


	public String getGoogle_client_secret() {
		return google_client_secret;
	}


	public String getGoogle_access_token_url() {
		return google_access_token_url;
	}


	public String getGoogle_redirect_url() {
		return google_redirect_url;
	}

	
}
