package kr.co.richardprj.swp.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class KakaoAPI20 extends DefaultApi20 implements SnsUrls{

	private static KakaoAPI20 _instance;
	private KakaoAPI20() {
		
	}
	
	private static class InstanceHolder{
		private static final KakaoAPI20 INSTANCE = new KakaoAPI20();
	}
	
	public static KakaoAPI20 instance() {

		return InstanceHolder.INSTANCE;
		 
	}
	
	@Override
	public String getAccessTokenEndpoint() {
		return KAKAO_ACCESS_TOKEN;
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return KAKAO_AUTH;
	}

}
