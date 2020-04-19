package kr.co.richardprj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.dto.MemberVO;

import kr.co.richardprj.dto.board.PaginationVO;
import kr.co.richardprj.dto.board.PostVO;
import kr.co.richardprj.service.board.BoardService;
import kr.co.richardprj.swp.interceptor.SessionNames;

@Controller
@PropertySource("classpath:/property/global.properties")
public class BoardController implements SessionNames{
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject 
	BoardService boardService;
	
	@Autowired
	ApplicationContext context;
	
	
	/**
	 * 게시물 한 건을 조회한다.
	 * @param model
	 * @param post
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/article.do", method = RequestMethod.POST)
	public String article(Model model, PostVO post, PaginationVO pagination) throws Exception {
		logger.info("article.do called....");
		boardService.increaseReadCnt(post);
		model.addAttribute("post", boardService.getPost(post));
		model.addAttribute("pagination",pagination);

		return "board/boardView";
	}
	
	/**
	 * 게시물을 저장한다.
	 * @param model
	 * @param post
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savePost.do", method = RequestMethod.POST)
	public String savePost(Model model, PostVO post, HttpSession sess) throws Exception {
		
		int rslt = 0;
		if(post.getPostNo() > 0) {
			rslt = boardService.updatePost(post);
		}else {
			MemberVO mem = (MemberVO)sess.getAttribute(LOGIN);
			post.setWriter(mem.getMbrid());
			rslt = boardService.insertPost(post);
		}
		
		if ( rslt > 1 ) {
			model.addAttribute("result", "save successfully");
		}else {
			model.addAttribute("result", "error ....");
		}
		
		model.addAttribute("boardId", post.getBoardId());

		return "redirect:/postList.do";


	}
	
	/**
	 * 첨부파일이 있는 게시물을 저장한다.
	 * @param model
	 * @param post
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savePost2.do", method = RequestMethod.POST)
	public String savePost2(Model model, PostVO post, HttpSession sess, MultipartHttpServletRequest mpRequest) throws Exception {
		
		logger.info("write post with attach files!!!");
		
		List<MultipartFile> fileList = mpRequest.getFiles("post_files");
		
		logger.info("attach file count is {}", fileList.size());
		
		int rslt = 0;
		if(post.getPostNo() > 0) {
			rslt = boardService.updatePost(post, mpRequest);
		}else {
			MemberVO mem = (MemberVO)sess.getAttribute(LOGIN);
			post.setWriter(mem.getMbrid());
			rslt = boardService.insertPost(post, mpRequest);
		}
		
		
		if ( rslt > 1 ) {
			model.addAttribute("result", "save successfully");
		}else {
			model.addAttribute("result", "error ....");
		}
		
		model.addAttribute("boardId", post.getBoardId());

		return "redirect:/postList.do";


	}
	
	
	@RequestMapping(value="/postDelete.do", method = RequestMethod.POST)
	public String postDelete(Model model, PostVO post, HttpSession sess) throws Exception {
		MemberVO mem = (MemberVO)sess.getAttribute(LOGIN);
		
		if ( StringUtils.equals(boardService.getPostOnly(post).getWriter(), mem.getMbrid() ) ) {
			boardService.deletePost(post);
			model.addAttribute("result", "deleted post successfully!");
		}else {
			model.addAttribute("result", "error ....");
		}
		model.addAttribute("boardId", post.getBoardId());
		model.addAttribute("pagination",post.getPagination());

		return "redirect:/postList.do";


	}
	
	
	/**
	 * 게시판의 게시물 목록을 가져온다.
	 * 페이징 기능이 구현되어 있다.
	 * @param model
	 * @param page
	 * @param range
	 * @param postVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/postList.do", method = {RequestMethod.GET, RequestMethod.POST})
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
		model.addAttribute("boardId", postVO.getBoardId());

		return "board/boardList";


	}
	/**
	 * 포스팅을 신규로 작성할 수 있는 페이지로 이동한다.
	 * 기존 포스트를 수정할 경우 postNo가 있으면 된다.
	 * @param model
	 * @param post
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/postWrite.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String board (Model model, PostVO post) throws Exception {
		if(post.getPostNo() > 0) {
			logger.info("postNo is {}", post.getPostNo());
			model.addAttribute("article", boardService.getPostOnly(post));
		}else {
			model.addAttribute("article", post);
		}
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/boardImage.do", method=RequestMethod.POST)
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String uploadedFile = saveFile(file);
            
            return ResponseEntity.ok().body("/summernoteImage/"+uploadedFile);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
	
	
	@GetMapping("/summernoteImage/{fileId}")
    @ResponseBody
    public void serveFile(@PathVariable String fileId, HttpServletResponse response) {
       
    	String fileName = fileId;
    	logger.info("summernoteImage file name is ==={}", fileId);
    	
		Environment env = context.getEnvironment();
		String UPLOAD_PATH = env.getProperty("board.upload.path");
		
		try {
			File file = new File(UPLOAD_PATH + File.separator + fileName);
			
			if (!file.exists()) {
				logger.info("file not found~~");
			}
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			/**** java.net.URLEncoder.encode , UTF-8 ,need to remove space at the end, **/
			response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
			response.setContentLength((int) file.length());
			
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			
			//use file copy utils from input stream to output stream
			FileCopyUtils.copy(fis, os);
			
			fis.close();
            os.close();
			
		} catch(IOException e) {
			logger.error(e.getMessage());
		}	
        	
    }
	
	//file upload into dedicated location
	private String saveFile(MultipartFile file){
	    // uuid 
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid+"";

	    logger.info("saveName: {}", saveName);
	    
	    Environment env = context.getEnvironment();
		String UPLOAD_PATH = env.getProperty("board.upload.path");
	    
		File saveFile = new File(UPLOAD_PATH,saveName); 
		
	    try {
	        file.transferTo(saveFile); 
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile
		

}
