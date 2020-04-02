package kr.co.richardprj.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KakaoLoginController {
	private static final Logger logger = LoggerFactory.getLogger(KakaoLoginController.class);

	@RequestMapping(value="/kakaoLoginHome.do", method= RequestMethod.GET)
	public String kakaoLoginHome (Model model) {
		
		
		logger.info("************");
		
		return "kakaoLogin";
	}
	
	@RequestMapping(value="/kakaoLoagin.do", method= RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> kakaoLogin (Model model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("memberCnt", result);
		
		return map;
	}
	
}
