package kr.co.richardprj.swp.auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

import com.example.dto.MemberVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import kr.co.richardprj.controller.MemberController;

public class SNSLogin {
	
	private OAuth20Service oauthService;
	
	private SnsValue sns;
	
	private static final Logger logger = LoggerFactory.getLogger(SNSLogin.class);
	
	public SNSLogin(SnsValue sns) {
		
		this.oauthService = new ServiceBuilder(sns.getClientId())
				.apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl())
//				.setScope("profile") //Basic_Profile
				.build(sns.getApi20Instance());

		this.sns = sns;
	}
	
	public String getSnsAuthURL() {
		return this.oauthService.getAuthorizationUrl();
	}
	
	public MemberVO getUserProfile(String code) throws Exception {
//		String access_token_json = getAccessTokenForKakao(code);
//		
//		MemberVO membervo = getUserProfileKakao(access_token_json);
		
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		OAuthRequest request = new OAuthRequest(Verb.GET, this.sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
		
		Response response = oauthService.execute(request);
		logger.info("respose body is {}", response.getBody());
		
		return parseJson(response.getBody());
//		return new MemberVO();
		
	}
	
	public String getAccessTokenForKakao(String code) throws Exception {
		
		final String tokenRequestUrl = sns.KAKAO_ACCESS_TOKEN;

	    String CLIENT_ID = sns.getClientId();//"[REST API Key]"; // 해당 앱의 REST API KEY 정보. 개발자 웹사이트의 대쉬보드에서 확인 가능
	    String REDIRECT_URI = sns.getRedirectUrl();//"[Redirect uri]"; // 해당 앱의 설정된 uri. 개발자 웹사이트의 대쉬보드에서 확인 및 설정 가능
	    //String code = "[Authorized code]"; // 로그인 과정중 얻은 authorization code 값

	    final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	    postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
	    postParams.add(new BasicNameValuePair("client_id", CLIENT_ID));
	    postParams.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
	    postParams.add(new BasicNameValuePair("code", code));
	    postParams.add(new BasicNameValuePair("client_secret", sns.getClientSecret()));

	    final HttpClient client = HttpClientBuilder.create().build();

	    final HttpPost post = new HttpPost(tokenRequestUrl);

	    // add header
	    //post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	    
	
	    BufferedReader rd = null;
	    InputStreamReader isr = null;
	    String response_string = "";
	    try {
	      post.setEntity(new UrlEncodedFormEntity(postParams));
	      
	      final HttpResponse response = client.execute(post);

	      final int responseCode = response.getStatusLine().getStatusCode();

	      System.out.println("\nSending 'POST' request to URL : " + tokenRequestUrl);
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
	      System.out.println("--------------\n\n access_token ==================="+buffer.toString());

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
	
	public MemberVO getUserProfileKakao(String s) throws Exception {
		
		System.out.println("\n\n code : " + s);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(s);
		String access_token = rootNode.get("access_token").asText();
		
		//final String AUTH_HOST = "https://kauth.kakao.com";
	    final String tokenRequestUrl = sns.KAKAO_PROFILE_URL;
	    
//	    Authorization: Bearer {access_token}
//	    Content-type: application/x-www-form-urlencoded;charset=utf-8

	    String CLIENT_ID = sns.getClientId();//"[REST API Key]"; // 해당 앱의 REST API KEY 정보. 개발자 웹사이트의 대쉬보드에서 확인 가능
	    String REDIRECT_URI = sns.getRedirectUrl();//"[Redirect uri]"; // 해당 앱의 설정된 uri. 개발자 웹사이트의 대쉬보드에서 확인 및 설정 가능
	    //String code = "[Authorized code]"; // 로그인 과정중 얻은 authorization code 값

	    final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
//	    postParams.add(new BasicNameValuePair("property_keys", "kakao_account.email"));
//	    postParams.add(new BasicNameValuePair("property_keys", "kakao_account.gender"));
	    

	    final HttpClient client = HttpClientBuilder.create().build();

	    final HttpPost post = new HttpPost(tokenRequestUrl);

	    // add header
	    post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	    
	    post.setHeader("Authorization", "Bearer "+access_token);
	    
	    //property_keys=["kakao_account.email"]
	    //conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	    
	    
	
	    BufferedReader rd = null;
	    InputStreamReader isr = null;
	    try {
	      post.setEntity(new UrlEncodedFormEntity(postParams));
	      //x-www-form-urlencoded
	      final HttpResponse response = client.execute(post);

	      final int responseCode = response.getStatusLine().getStatusCode();

	      System.out.println("\nSending 'POST' request to URL : " + tokenRequestUrl);
	      System.out.println("Post parameters : " + postParams);
	      System.out.println("Response Code : " + responseCode);
	      
	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }

	      System.out.println("--------------\n\n profile=================================="+buffer.toString());

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
		}else if(this.sns.isKakao()) {
			
			System.out.println("kakao~~~~~~\n\n" + body);
//			JsonNode resNode = rootNode.get("response");
//			member.setNaverId(resNode.get("id").asText());
//			member.setMbrName(resNode.get("name").asText());
//			member.setMbrEmail(resNode.get("email").asText());
		}
		
		
		return member; 
	}
	
	
}
