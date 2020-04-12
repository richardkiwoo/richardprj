package kr.co.richardprj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.example.dto.MemberVO;
import com.example.service.MemberService;

import kr.co.richardprj.service.SnsLoginService;
import kr.co.richardprj.swp.auth.SnsValue;
import kr.co.richardprj.swp.interceptor.SessionNames;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	private MemberService memberService;
	
	@Inject
	private SnsLoginService snsLoginService;
	
	@Inject
	private SnsValue naverSns;
	
	@Inject
	private SnsValue kakaoSns;
	
	@Inject
	private SnsValue googleSns;
	
	@Inject
	private GoogleConnectionFactory googleConnectionFactory;
	
	@Inject
	private OAuth2Parameters googleOAuth2Parameters;
	
	/*
	 * login page view and set sns urls.
	 */
	@RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
	public String loginPage(Model model) throws Exception {
		
		logger.info("snsLogin GET ....");
		//SNSLogin_delete snsLogin = new SNSLogin_delete(naverSns);
		model.addAttribute("naver_url", snsLoginService.getSnsUrls(naverSns));
		model.addAttribute("kakao_url", snsLoginService.getSnsUrls(kakaoSns));
		
		//google은 다른방식으로 url 가져온다.
//		SNSLogin googleLogin = new SNSLogin(googleSns);
//		model.addAttribute("google_url", googleLogin.getSnsAuthURL());
		
		/* 구글code 발행을 위한 URL 생성 */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		model.addAttribute("google_url",url);
		
		return "login";
	}
	
	/*
	 * SNS Login을 위한 메소드
	 * 1. servlet-context에 구글, 네이버 설정정보를 기입하고 inject 받아서 사용한다.
	 * 2. SnsUrls에는 url을 가지고 있고, SnsValue파일은 VO와 같은 역할을 한다.
	 * 3. SNSLogin 에서 scribejava 가 code값을 받아서 access token을 받아서 재차 user profile을 받아오는 기능을 한다.
	 * 4. 받아온 json 데이터를 회원가입 시키거나, 로그인 처리 한다.
	 * 5. 카카오는 scribejava가 오류나서 개별로 개발하여 처리한다.
	 */
	@RequestMapping(value="/auth/{service}/callback", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void snsLoginCallback(@PathVariable String service, 
			Model model, @RequestParam String code, HttpSession sess) throws Exception {
		
		logger.info("snsLoginCallback : service={}", service);
		SnsValue sns = null;
		if(StringUtils.equalsIgnoreCase("naver", service))
			sns = naverSns;
		else if(StringUtils.equalsIgnoreCase("google", service))
			sns = googleSns;
		else
			sns = kakaoSns;
		//1. code를 이용해서 access_token 받기
		//2. access_token을 이용해서 사용자 profile 정보 가져오기
		
		//3. DB 해당 유저가 존재하는지 체크 (googleid, naverid 컬럼 추가)
		//4. 존재시 강제로그인, 미존재시 가입페이지로!! 
		
		MemberVO member = null ;
		
		if(StringUtils.equalsIgnoreCase("kakao", service))
			member = snsLoginService.getUserProfileKakao(code);
		else {
			member = snsLoginService.getSnsUserProfile(sns,code);
		}
		
		if ( member != null) {
			sess.setAttribute("loginInfo", member);
			model.addAttribute("member", member);
			model.addAttribute("result", "Contratulatuons!");
		}else {
			model.addAttribute("result", "Sorry! there's error has occurred when login!");
		}
		
		//return "redirect:/";
	}
	
	
	@RequestMapping(value = "/registerform.do",  method = RequestMethod.GET)
	public String registerForm() {
		
		return "register_form";
	}
	
	 
	@RequestMapping(value = "/register.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView register(MemberVO member) throws Exception {
		
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		 
		 String cpPwd = scpwd.encode(member.getMbrpw());
        //암호화 하여 password에 저장
        member.setCpPw(cpPwd);
        member.setMbrpw(cpPwd);
        
        int reuslt = memberService.registerMember(member);
		
		ModelAndView mav = new ModelAndView("register_form");
		if (reuslt > 0) {
			mav.addObject("message", "가입처리가 완료되었습니다.");
		}
	    return mav;
	}
	
	@RequestMapping(value = "/checkID.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public  Map<String, Object> checkID( MemberVO member) throws Exception {
		
		logger.info("Member");
		
		int result = memberService.checkID(member);
//		ModelAndView mav = new ModelAndView("jsonView");
//		mav.addObject("memberCnt", result);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCnt", result);
	
        return map;
	}
	
	
	@RequestMapping(value= "/login.do", method = RequestMethod.POST)
	public  void login(HttpServletRequest req, MemberVO member, Model model ) throws Exception {
		
		MemberVO mem = memberService.login(member);
		if(mem == null) {
			String msg = "사용자가 없습니다";
			model.addAttribute("mgs", msg);
		}else {
			logger.info("memberid==="+mem.getMbrName());
			
			BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
			if (scpwd.matches(member.getMbrpw(), mem.getMbrpw())) {
				req.getSession().setAttribute("loginInfo", mem);
				model.addAttribute("member", mem);

			}else {
				model.addAttribute("mgs", "비빌번호가 틀렸습니다.");
			}
			
		}
		
		//return mav; 
	}
	
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String handleRequestlogout(Model model, 
			HttpServletRequest request,HttpSession sess,
			HttpServletResponse response) {

		//HttpSession sess = request.getSession(false);
		//request.getSession(true).invalidate();
		sess.invalidate();
		model.addAttribute("result", "Logout successfully!");
		
		//cookie 제거
		Cookie loginCookie = WebUtils.getCookie(request, SessionNames.LOGIN);
		if (loginCookie != null) {
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			
			response.addCookie(loginCookie);
		}
	
		return "home";

	}
	
	
	@RequestMapping(value= "/memberList.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMemberList(Model model, MemberVO memberVO) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberVO.setMbrid(null);
		List<MemberVO> members = memberService.selectMember(memberVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("members", members);
		
		return map;
	}
	
}
