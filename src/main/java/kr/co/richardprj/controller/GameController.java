package kr.co.richardprj.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.controller.HomeController;

import kr.co.richardprj.dto.GameVO;
import kr.co.richardprj.service.GameService;

@Controller
public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private GameService gameService;
	
	@RequestMapping(value = "/game.do", method = RequestMethod.GET)
	public String game(GameVO gameVO) throws Exception {
		return "game_result";
	}
	
	@RequestMapping(value = "/gameList.do", method = RequestMethod.GET)
	public ModelAndView gameList(GameVO gameVO) throws Exception {
		List<GameVO> games = gameService.getGameList();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("game_list");
		mav.addObject("gameList", games);
		
		return mav;
	}
	
	@RequestMapping(value = "/saveGameResult.do", method=RequestMethod.POST)
	public ModelAndView saveGameResult (GameVO gameVO) throws Exception {
		logger.info("saveGameResult");
		
		ModelAndView mav = new ModelAndView();
		int result = gameService.insertGameResult(gameVO);
		mav.setViewName("game_view");
		if ( result > 0 )
			mav.addObject("result", "저장하였습니다!");
		else
			mav.addObject("result", "저장에 실패하였습니다. 다시 시도해 주세요!");	
		
		return mav;
				
	}
	
}
