package com.example.controller;

import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.service.MemberService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private MemberService service;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception{

		logger.info("home");

		
		//List<MemberVO> memberList = service.selectMember();
		
		//model.addAttribute("memberList", memberList);
		
		/*
		 *  암호화 복호화 확인 소스
		 * StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		 * encryptor.setAlgorithm("PBEWITHMD5ANDDES"); encryptor.setPassword("richard");
		 * String encryptedPass = encryptor.encrypt("richard"); String decryptedPass =
		 * encryptor.decrypt(encryptedPass);
		 * System.out.println("Encrypted Password for admin is : "+encryptedPass);
		 * System.out.println("Decrypted Password for admin is : "+decryptedPass);
		 */

		return "home";
	}
	
}
