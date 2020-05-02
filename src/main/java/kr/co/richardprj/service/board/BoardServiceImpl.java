package kr.co.richardprj.service.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.dao.MemberDAO;

import kr.co.richardprj.dao.board.BoardDAO;
import kr.co.richardprj.dto.board.AttachFileVO;
import kr.co.richardprj.dto.board.BoardVO;
import kr.co.richardprj.dto.board.ContentsVO;
import kr.co.richardprj.dto.board.PostVO;
import kr.co.richardprj.dto.board.ReplyVO;

@Service
@PropertySource("classpath:/property/global.properties")
public class BoardServiceImpl implements BoardService{
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Inject
	private BoardDAO boardDao; 
	
	@Inject
	private MemberDAO memberDao; 
	
	@Autowired
	ApplicationContext context;
	
	
	
	@Override
	public List<PostVO> getPostList(PostVO post) throws Exception {
		//post 에 댓글수 표시, 작성자명 표시
		return boardDao.selectPostList(post);
	}

	@Override
	public int selectPostListCnt(PostVO post) throws Exception {
		return boardDao.selectPostListCnt(post);
	}
	
	@Override
	public PostVO getPost(PostVO post) throws Exception {
		PostVO p = boardDao.selectPost(post);
		ContentsVO cont = boardDao.selectContents(post);
		p.setContents(cont == null ? "" : cont.getContents());
		
		List<AttachFileVO> afList = boardDao.selectAttachFileList(post);
		p.setRecommendCnt(boardDao.selectRecommendCnt(post));
		p.setAttachFile(afList);
		p.setReplies(boardDao.selectReplyList(post));
		
		return p;
	}
	@Override
	public PostVO getPostOnly(PostVO post) throws Exception {
		PostVO p = boardDao.selectPost(post);
		return p;
	}
	
	@Override
	public ReplyVO getReply(ReplyVO reply) throws Exception {
		ReplyVO rep = boardDao.selectReply(reply);
		return rep;
	}
	
	@Override
	public int increaseReadCnt(PostVO post) throws Exception {
		return boardDao.increaseReadCnt(post);
	}		

	@Override
	public int insertPost(PostVO post) throws Exception {
		int pno = boardDao.selectMaxPostNo(post);
		post.setPostNo(pno + 1);
		int r = boardDao.insertPost(post);
		
		int r2 = boardDao.insertContents(post);
		return r + r2;
	}
	
	@Override
	public int insertPost(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception {
		int pno = boardDao.selectMaxPostNo(post);
		post.setPostNo(pno + 1);
		post.setDelYn("N");
		
		logger.info("insert Post is {}", post.toString());
		
		int r = boardDao.insertPost(post);
		int r2 = boardDao.insertContents(post);
		int r3 = attachFileHandler(post, mpRequest);
		
		return r + r2;
	}

	@Override
	public int updatePost(PostVO post) throws Exception {
		
		int r1 = boardDao.updatePost(post);
		int r2 = boardDao.updateContents(post);
		return r1 + r2;
	}
	
	@Override
	public int updatePost(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception {
		logger.info("update Post is {}", post.toString());
		
		int r1 = boardDao.updatePost(post);
		int r2 = boardDao.updateContents(post);
		int r3 = attachFileHandler(post, mpRequest);
		return r1 + r2;
	}
	
	@Override
	public int deletePost(PostVO post) throws Exception {
		logger.info("delete post {}", post.toString());
		//int r1 = boardDao.deleteContents(post);
		//int r2 = boardDao.deletePost(post);
		//update delYn flag instead of delete record..
		post.setDelYn("Y");
		post.setDispYn(null);
		post.setTopPostYn(null);
		int r2 = boardDao.updatePost(post);
		
		return r2 ;
	}
	
	@Override
	public int updateReply(ReplyVO reply) throws Exception {
		return boardDao.updateReply(reply);
	}

	@Override
	public int deleteReply(ReplyVO reply) throws Exception {
		return boardDao.deleteReply(reply);
	}

	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public int attachFileHandler(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception {
		List<MultipartFile> fileList = mpRequest.getFiles("post_files");


		AttachFileVO af = new AttachFileVO();
		af.setBoardId(post.getBoardId());
		af.setPostNo(post.getPostNo());
		
		Environment env = context.getEnvironment();
		String UPLOAD_PATH = env.getProperty("upload.path");
		
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		int i = 0;
		for(MultipartFile f : fileList){
			storedFileName = saveFile(f);
			
			originalFileName = f.getOriginalFilename();
			
			if(originalFileName == null || StringUtils.equals(originalFileName, ""))
				break;
			
			originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			
			af.setFileSeq(++i);
			af.setFileName(originalFileName);
			af.setSavedFileName(storedFileName);
			af.setFilePath(UPLOAD_PATH);
			af.setFileSize(f.getSize());
			af.setFileType(originalFileExtension.substring(1));
			boardDao.insertAttachFile(af);
			
	    }
		
		return i;
	}
	
	private String saveFile(MultipartFile file){
	    // uuid 
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();

	    logger.info("saveName: ",saveName);
	    Environment env = context.getEnvironment();
		
		String UPLOAD_PATH = env.getProperty("upload.path");
	    File saveFile = new File(UPLOAD_PATH,saveName); 

	    try {
	        file.transferTo(saveFile); 
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile

	@Override
	public AttachFileVO getAttachFile(AttachFileVO file) throws Exception {
		return boardDao.selectAttachFile(file);
	}

	@Override
	public ReplyVO insertReply(ReplyVO reply) throws Exception {
		reply.setReplyNo(boardDao.selectMaxReplyNo(reply)+1);
		int r1 = boardDao.insertReply(reply);
		if (r1 < 1)
			reply = null;
		return reply;
	}

	@Override
	public BoardVO getBoard(BoardVO board) throws Exception {
		return boardDao.selectBoard(board);
	}

}
