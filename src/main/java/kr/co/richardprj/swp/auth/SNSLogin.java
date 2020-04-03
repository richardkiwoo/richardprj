package kr.co.richardprj.swp.auth;

import com.example.dto.MemberVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class SNSLogin {
	
	private OAuth20Service oauthService;
	
	private SnsValue sns;
	 
	
	public SNSLogin(SnsValue sns) {
		this.oauthService = new ServiceBuilder(sns.getClientId())
				.apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl())
				.scope("profile")
				.build(sns.getApi20Instance());

		this.sns = sns;
	}
	
	public String getSnsAuthURL() {
		return this.oauthService.getAuthorizationUrl();
	}
	
	public MemberVO getUserProfile(String code) throws Exception {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, this.sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
		
		Response response = oauthService.execute(request);
		
		return parseJson(response.getBody());
	}
	
	private MemberVO parseJson(String body) throws Exception {
		
		MemberVO member = new MemberVO();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(body);
		
		if(this.sns.isGoogle()) {
			member.setGoogleId(rootNode.get("sub").asText());
			member.setMbrEmail(rootNode.get("email").asText());
			
			// 아래는 GOOGLE_PROFILE_URL 에 따라서 나타나는 결과가 다르다
			// vi/people.me 는 depricated된 것으로 나타남.
//			  "sub": "102973424222856050882",
//			  "picture": "https://lh6.googleusercontent.com/-4Zbt5f6Bbbs/AAAAAAAAAAI/AAAAAAAAAAA/MtCDtsGDkwQ/photo.jpg",
//			  "email": "richardkiwoo@gmail.com",
//			  "email_verified": true
			
//			String id = rootNode.get("id").asText();
//			member.setMbrid(id);
//			
//			String displayName = rootNode.get("displayName").asText();
//			JsonNode nameNode = rootNode.path("name");
//			String name = nameNode.get("familyName").asText() + nameNode.get("givenName").asText();
//			member.setMbrName(displayName);
//			
//			Iterator<JsonNode> iterEmails = rootNode.findPath("emails").elements();
//			while (iterEmails.hasNext()) {
//				JsonNode emailNode = iterEmails.next();
//				String type = emailNode.get("type").asText();
//				if(StringUtils.equals(type, "account")) {
//					String email = emailNode.get("value").asText();
//					member.setMbrEmail(email);
//					break;
//				}
//				
//			}
		}else if(this.sns.isNaver()) {
			JsonNode resNode = rootNode.get("response");
			member.setNaverId(resNode.get("id").asText());
			member.setMbrName(resNode.get("name").asText());
			member.setMbrEmail(resNode.get("email").asText());
		}
		
		
		return member; 
	}
	
	
	
}
