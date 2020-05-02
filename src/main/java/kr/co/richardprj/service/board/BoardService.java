package kr.co.richardprj.service.board;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.richardprj.dto.board.AttachFileVO;
import kr.co.richardprj.dto.board.BoardVO;
import kr.co.richardprj.dto.board.PostVO;
import kr.co.richardprj.dto.board.ReplyVO;

public interface BoardService {
	public BoardVO getBoard(BoardVO board) throws Exception;
	
	public List<PostVO> getPostList(PostVO post) throws Exception;
	public int selectPostListCnt(PostVO post) throws Exception;
	public PostVO getPost(PostVO post) throws Exception;
	public PostVO getPostOnly(PostVO post) throws Exception;
	public AttachFileVO getAttachFile(AttachFileVO file) throws Exception;
	public int insertPost(PostVO post) throws Exception;
	public int insertPost(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception;
	public int increaseReadCnt(PostVO post) throws Exception;
	public int updatePost(PostVO post) throws Exception;
	public int updatePost(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception;
	public int deletePost(PostVO post) throws Exception;
	
	public ReplyVO getReply(ReplyVO reply) throws Exception;
	public ReplyVO insertReply(ReplyVO reply) throws Exception;
	public int updateReply(ReplyVO reply) throws Exception;
	public int deleteReply(ReplyVO reply) throws Exception;
	
}
