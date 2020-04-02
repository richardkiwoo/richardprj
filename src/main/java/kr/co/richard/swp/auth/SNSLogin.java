package kr.co.richard.swp.auth;

import java.util.Iterator;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.social.oauth2.OAuth2Parameters;

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
	public String getNaverAuthURL() {
		
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
		
		System.out.println("************************\n"+ body + "\n************************");
		MemberVO member = new MemberVO();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(body);
		
		if(this.sns.isGoogle()) {
			String id = rootNode.get("id").asText();
			member.setMbrid(id);
			
			String displayName = rootNode.get("displayName").asText();
			JsonNode nameNode = rootNode.path("name");
			String name = nameNode.get("familyName").asText() + nameNode.get("givenName").asText();
			member.setMbrName(displayName);
			
			Iterator<JsonNode> iterEmails = rootNode.findPath("emails").elements();
			while (iterEmails.hasNext()) {
				JsonNode emailNode = iterEmails.next();
				String type = emailNode.get("type").asText();
				if(StringUtils.equals(type, "account")) {
					String email = emailNode.get("value").asText();
					member.setMbrEmail(email);
					break;
				}
				
			}
		}else if(this.sns.isNaver()) {
			JsonNode resNode = rootNode.get("response");
			member.setNaverId(resNode.get("id").asText());
			member.setMbrName(resNode.get("nickName").asText());
			member.setMbrEmail(resNode.get("email").asText());
		}
		
		
		return member; 
	}
	
	
	
}
