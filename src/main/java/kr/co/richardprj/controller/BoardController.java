package kr.co.richardprj.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.richardprj.dto.board.PaginationVO;
import kr.co.richardprj.dto.board.PostVO;
import kr.co.richardprj.service.board.BoardService;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject 
	BoardService boardService;
	
	@RequestMapping(value="/postList.do", method = RequestMethod.GET)
	public String board (Model model
			, @RequestParam(required = false, defaultValue = "1") int page
			, @RequestParam(required = false, defaultValue = "1") int range
			, PostVO postVO
			) throws Exception {
		
		int listCnt = boardService.selectPostListCnt(postVO);
		
		PaginationVO pagination = new PaginationVO();
		
		pagination.pageInfo(page, range, listCnt);
		
		postVO.setPagination(pagination);
		
		model.addAttribute("pagination", pagination);

		model.addAttribute("postList", boardService.getPostList(postVO));

		return "board/boardList";


	}
}
