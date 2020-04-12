package kr.co.richardprj.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.dao.MemberDAO;
import com.example.dto.MemberVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import kr.co.richardprj.swp.auth.SnsValue;

@PropertySource("classpath:/property/global.properties")
@Service
public class SnsLoginServiceImpl implements SnsLoginService{
	private static final Logger logger = LoggerFactory.getLogger(SnsLoginServiceImpl.class);
	
	@Autowired
	ApplicationContext context;
	
	@Inject
	private MemberDAO dao;
	
	@Override
	public int snsLogin(String code) throws Exception {
		return 0;
	}
	
	@Override
	public String getSnsUrls(SnsValue sns) throws Exception {
		
		OAuth20Service oauthService = new ServiceBuilder(sns.getClientId())
				.apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl())
				.build(sns.getApi20Instance());
		
		return oauthService.getAuthorizationUrl();
	}
	
	
	@Override
	public MemberVO getSnsUserProfile(SnsValue sns, String code) throws Exception {
		
		OAuth20Service oauthService = new ServiceBuilder(sns.getClientId())
				.apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl())
//				.setScope("profile") //Basic_Profile
				.build(sns.getApi20Instance());
		
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		OAuthRequest request = new OAuthRequest(Verb.GET, sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
		
		Response response = oauthService.execute(request);
		logger.info("respose body is {}", response.getBody());
		
		
		MemberVO m = new MemberVO();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(response.getBody());
		
		if(sns.isGoogle()) {
			m.setMbrid(rootNode.get("sub").asText());
			m.setGoogleId(rootNode.get("sub").asText());
			m.setMbrEmail(rootNode.get("email").asText());
			
			m.setMbrName("google_user");
			m.setMbrpw("google_user");
			//m.setNikname(nickname);
			//m.setGender(gender);
		}else if(sns.isNaver()) {
			JsonNode nameNode = rootNode.path("response");
			
			m.setMbrid(nameNode.get("id").asText());
			m.setNaverId(nameNode.get("id").asText());
			m.setMbrpw("naver_user");
			m.setMbrName(nameNode.get("name").asText());
			m.setNikname(nameNode.get("name").asText());
			m.setGender(nameNode.get("gender").asText());
			m.setMbrEmail(nameNode.get("email").asText());
		}
		
		List<MemberVO> mlist = dao.selectMember(m);
		int result = 0;
		if(mlist.size() > 0) {
			m =  mlist.get(0);
		}else {
			//신규 가입 처리
			result = dao.registerMember(m);
		}
		
		return m;
	}
	
	
	@Override
	public MemberVO getUserProfileKakao(String code) throws Exception {
		String access_token = getAccessTokenForWithCode(code);
		String profile = getUserProfile(access_token);
		
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(profile);
		String kakaoId = rootNode.get("id").asText();
		String gender = null;//rootNode.get("gender").asText();
		
		
		JsonNode nameNode = rootNode.path("properties");
		String nickname = nameNode.get("nickname").asText();
		
		
//		nameNode = rootNode.path("kakao_account");
//		String email = nameNode.get("email").asText();
//		String age_range = rootNode.get("age_range").asText();

		
		MemberVO m = new MemberVO();
		m.setMbrid(kakaoId);
		m.setKakaoId(kakaoId);
		m.setMbrName(nickname);
		m.setMbrpw("kakao_user");
		m.setMbrEmail("to be set");
		m.setNikname(nickname);
		m.setGender(gender);
		
		List<MemberVO> mlist = dao.selectMember(m);
		
		int result = 0;
		if(mlist.size() > 0) {
			m =  mlist.get(0);
		}else {
			//신규 가입 처리
			result = dao.registerMember(m);
		}
//		if (result == 0) m = null;
		return m;
	}
	
	public String getAccessTokenForWithCode(String code) throws Exception {

		Environment env = context.getEnvironment();
		
		String CLIENT_ID = env.getProperty("oauth.kakao.client.id");
		String CLIENT_SECRET = env.getProperty("oauth.kakao.client.secret");
		String ACCESS_TOKEN_URL = env.getProperty("oauth.kakao.accesstokenurl");
		String REDIRECT_URL = env.getProperty("oauth.kakao.redirecturl");
		
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	    postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
	    postParams.add(new BasicNameValuePair("client_id", CLIENT_ID));
	    postParams.add(new BasicNameValuePair("redirect_uri", REDIRECT_URL));
	    postParams.add(new BasicNameValuePair("code", code));
	    postParams.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));

	    final HttpClient client = HttpClientBuilder.create().build();

	    final HttpPost post = new HttpPost(ACCESS_TOKEN_URL);

	    // add header
	    //post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	    
	
	    BufferedReader rd = null;
	    InputStreamReader isr = null;
	    String response_string = "";
	    try {
	      post.setEntity(new UrlEncodedFormEntity(postParams));
	      
	      final HttpResponse response = client.execute(post);

	      final int responseCode = response.getStatusLine().getStatusCode();

	      logger.info("\nSending 'POST' request to URL : {}", ACCESS_TOKEN_URL);
	      logger.info("Post parameters : " + postParams);
	      logger.info("Response Code : " + responseCode);
	      
	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }
	      
	      response_string = buffer.toString();
	      logger.info("access_token for kakao ==={}", buffer.toString());

	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
		
		return response_string;
		
	}
	
	public String getUserProfile(String s) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(s);
		String access_token = rootNode.get("access_token").asText();
		
		Environment env = context.getEnvironment();
		String profileRequestUrl = env.getProperty("oauth.kakao.profileurl");
		
	    final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	    final HttpClient client = HttpClientBuilder.create().build();
	    final HttpPost post = new HttpPost(profileRequestUrl);

	    // add header
	    post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	    post.setHeader("Authorization", "Bearer "+access_token);
	    
	    //property_keys=["kakao_account.email"]
	    //conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	    
	    BufferedReader rd = null;
	    InputStreamReader isr = null;
	    String json_profile = null;
	    try {
	      post.setEntity(new UrlEncodedFormEntity(postParams));
	      final HttpResponse response = client.execute(post);
	      final int responseCode = response.getStatusLine().getStatusCode();

	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd  = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }

	      logger.info("--------------\n\n profile for kakao ===\n"+buffer.toString()+"\n\n\n");
	      json_profile = buffer.toString();

	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
		
		return json_profile;
		
	}



}
