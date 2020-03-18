package kr.co.richardprj.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.controller.HomeController;
import com.example.dto.MemberVO;
import com.example.service.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private MemberService memberService;
	
	@RequestMapping(value = "/registerform.do",  method = RequestMethod.GET)
	public String registerForm() {
		
		return "register_form";
	}
	
	 
	@RequestMapping(value = "/register.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView register(MemberVO member) throws Exception {
		
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		 
		//암호화 하기전
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
	
	@RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping(value= "/login.do", method = RequestMethod.POST)
	public  ModelAndView login(HttpServletRequest req, MemberVO member ) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
//		if(req.getSession().getAttribute("loginInfo") != null) {
//			String msg = "이미 로그인된 상태입니다.";
//			mav.addObject("msg", msg);
//			mav.setViewName("login");
//		}else {
//			mav.setViewName("home");
//		}
		
		MemberVO mem = memberService.login(member);
		if(mem == null) {
			String msg = "사용자가 없습니다.";
			mav.addObject("msg", msg);
			mav.setViewName("login");
		}else {
			logger.info("memberid==="+mem.getMbrName());
			
			BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
			if (scpwd.matches(member.getMbrpw(), mem.getMbrpw())) {
				req.getSession().setAttribute("loginInfo", mem);
				mav.setViewName("home");
			}else {
				String msg = "비빌번호가 틀렸습니다.";
				mav.addObject("msg", msg);
				mav.setViewName("login");
			}
			
		}
		
		return mav; 
	}
	

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String handleRequestlogout(MemberVO memberVO, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		HttpSession sess = request.getSession(false);
		request.getSession(true).invalidate();
	
		return "home";

	}
}
