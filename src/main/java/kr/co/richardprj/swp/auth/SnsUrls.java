package kr.co.richardprj.swp.auth;

public interface SnsUrls {
	static final String NAVER_ACCESS_TOKEN = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	static final String NAVER_AUTH = "https://nid.naver.com/oauth2.0/authorize";
	static final String NAVER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
	
	static final String GOOGLE_PROFILE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
	// 아래 거는 안되는거임. 위에걸로 변경함.
	//static final String GOOGLE_PROFILE_URL = "https://www.googleapis.com/plus/v1/people.me";
	
	static final String KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
	static final String KAKAO_ACCESS_TOKEN = "https://kauth.kakao.com/oauth/token";
	static final String KAKAO_AUTH = "https://kauth.kakao.com/oauth/authorize";
	
}
