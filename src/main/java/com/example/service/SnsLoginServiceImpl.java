package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.example.dto.MemberVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.richardprj.swp.auth.SnsValue2;

@Service
public class SnsLoginServiceImpl implements SnsLoginService{
	private static final Logger logger = LoggerFactory.getLogger(SnsLoginServiceImpl.class);
	
	@Override
	public String sns_url_naver() throws Exception {
		SnsValue2 snsValue = new SnsValue2();
		return snsValue.NAVER_AUTH;
	}

	@Override
	public String sns_url_kakao() throws Exception {
		SnsValue2 snsValue = new SnsValue2();
		return snsValue.KAKAO_AUTH;
	}

	@Override
	public String sns_url_google() throws Exception {
		SnsValue2 snsValue = new SnsValue2();
		GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(snsValue.getGoogle_client_id(), snsValue.getGoogle_client_secret());
		
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		
		OAuth2Parameters googleOAuth2Parameters = new OAuth2Parameters();
		googleOAuth2Parameters.setScope("https://www.googleapis.com/auth/userinfo.email");
		googleOAuth2Parameters.setRedirectUri(snsValue.getGoogle_redirect_url());
		
		return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
	}

	@Override
	public int snsLogin(String code) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public String getAccessTokenForWithCode(String snsName, String code) throws Exception {
		SnsValue2 snsValue = new SnsValue2();
//		Environment env = context.getEnvironment();
//		final String naver_client_id = env.getProperty("oauth.naver.client.id");
//		final String naver_client_secret = env.getProperty("oauth.naver.client.secret");
//		final String naver_access_token_url = env.getProperty("oauth.naver.accesstokenurl");
//		final String naver_redirect_url = env.getProperty("oauth.naver.redirecturl");
//		
//		
//		final String kakao_client_id = env.getProperty("oauth.kakao.client.id");
//		final String kakao_client_secret = env.getProperty("oauth.kakao.client.secret");
//		final String kakao_access_token_url = env.getProperty("oauth.kakao.accesstokenurl");
//		final String kakao_redirect_url = env.getProperty("oauth.kakao.redirecturl");
//		
//		final String google_client_id = env.getProperty("oauth.google.client.id");
//		final String google_client_secret = env.getProperty("oauth.google.client.secret");
//		final String google_access_token_url = env.getProperty("oauth.google.accesstokenurl");
//		final String google_redirect_url = env.getProperty("oauth.google.redirecturl");
		
		String CLIENT_ID ="", CLIENT_SECRET="", ACCESS_TOKEN_URL="", REDIRECT_URL ="";
		
		if(StringUtils.equalsIgnoreCase("naver", snsName)){
			CLIENT_ID = snsValue.getNaver_client_id();
			REDIRECT_URL = snsValue.getNaver_redirect_url();
			CLIENT_SECRET = snsValue.getNaver_client_secret();
			ACCESS_TOKEN_URL = snsValue.getNaver_access_token_url();
		}else if(StringUtils.equalsIgnoreCase("kakao", snsName)){
			CLIENT_ID = snsValue.getKakao_client_id();
			REDIRECT_URL = snsValue.getKakao_redirect_url();
			CLIENT_SECRET = snsValue.getKakao_client_secret();
			ACCESS_TOKEN_URL = snsValue.getKakao_access_token_url();
		}else if(StringUtils.equalsIgnoreCase("google", snsName)){
//			CLIENT_ID = google_client_id;
//			REDIRECT_URL = google_redirect_url;
//			CLIENT_SECRET = google_client_secret;
//			ACCESS_TOKEN_URL = google_access_token_url;
		}
		
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

	      System.out.println("\nSending 'POST' request to URL : " + ACCESS_TOKEN_URL);
	      System.out.println("Post parameters : " + postParams);
	      System.out.println("Response Code : " + responseCode);
	      
	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }
	      
	      response_string = buffer.toString();
	      logger.info("access_token ==================={}", buffer.toString());

	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	        // clear resources
	        if (rd != null) {
	            try {
	                rd.close();
	            } catch(Exception ignore) {
	            }
	        }
	        if (isr != null) {
	            try {
	                isr.close();
	            } catch(Exception ignore) {
	            }
	         }
	    }
		
		
		
		return response_string;
		
	}
	
	public MemberVO getUserProfile(String snsName, String s) throws Exception {
		
		logger.info("access token is "+snsName + " is {}",  s);
		SnsValue2 snsValue = new SnsValue2();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(s);
		String access_token = rootNode.get("access_token").asText();
		String profileRequestUrl = "";
		
		if(StringUtils.equalsIgnoreCase("naver", snsName)){
			profileRequestUrl = snsValue.NAVER_PROFILE_URL;
		}else if(StringUtils.equalsIgnoreCase("kakao", snsName)){
			profileRequestUrl = snsValue.KAKAO_PROFILE_URL;
		}else if(StringUtils.equalsIgnoreCase("google", snsName)){
			profileRequestUrl = snsValue.GOOGLE_PROFILE_URL;
		}

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
	    try {
	      post.setEntity(new UrlEncodedFormEntity(postParams));
	      final HttpResponse response = client.execute(post);
	      final int responseCode = response.getStatusLine().getStatusCode();

	      logger.info("Sending 'POST' request to URL : " + profileRequestUrl);
	      logger.info("Post parameters : " + postParams);
	      logger.info("Response Code : " + responseCode);
	      
	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }

	      logger.info("--------------\n\n profile=================================="+buffer.toString());

	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	        // clear resources
	        if (rd != null) {
	            try {
	                rd.close();
	            } catch(Exception ignore) {
	            }
	        }
	        if (isr != null) {
	            try {
	                isr.close();
	            } catch(Exception ignore) {
	            }
	         }
	    }
		
		return new MemberVO();
		
	}

//	
//	OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
//	String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
	
	
}
