package kr.co.richardprj.dao.board;

import java.util.List;

import kr.co.richardprj.dto.board.AttachFileVO;
import kr.co.richardprj.dto.board.BoardVO;
import kr.co.richardprj.dto.board.ContentsVO;
import kr.co.richardprj.dto.board.PostVO;
import kr.co.richardprj.dto.board.RecommendVO;
import kr.co.richardprj.dto.board.ReplyVO;

public interface BoardDAO {
	
	/** 게시판 **/
	public List<BoardVO> selectBoardList(BoardVO board) throws Exception;
	public BoardVO selectBoard(BoardVO board) throws Exception;
	public int insertBoard(BoardVO board) throws Exception ;
	public int updateBoard(BoardVO board) throws Exception;
	public int deleteBoard(BoardVO board) throws Exception;
	
	/** 게시글 **/
	public List<PostVO> selectPostList(PostVO post) throws Exception;
	public PostVO selectPost(PostVO post) throws Exception;
	public int  selectMaxPostNo(PostVO post) throws Exception;
	public int selectPostListCnt(PostVO post) throws Exception ;
	public int increaseReadCnt(PostVO post) throws Exception ;
	public int insertPost(PostVO post) throws Exception ;
	public int updatePost(PostVO post) throws Exception;
	public int deletePost(PostVO post) throws Exception;
	
	public ContentsVO selectContents(PostVO post) throws Exception;
	public int insertContents(PostVO post) throws Exception ;
	public int updateContents(PostVO post) throws Exception;
	public int deleteContents(PostVO post) throws Exception;
	
	/** 댓글 **/
	public List<ReplyVO> selectReplyList(ReplyVO rep) throws Exception;
	public ReplyVO selectReply(ReplyVO rep) throws Exception;
	public int insertReply(ReplyVO rep) throws Exception ;
	public int updateReply(ReplyVO rep) throws Exception;
	public int deleteReply(ReplyVO rep) throws Exception;
	
	/** 추천 **/
	public int insertRecommend(RecommendVO recommend) throws Exception ;
	public int deleteRecommend(RecommendVO recommend) throws Exception;
	
	/** 첨부파일 **/
	public List<AttachFileVO> selectAttachFileList(PostVO post) throws Exception;
	public int insertAttachFile(AttachFileVO af) throws Exception ;
	public int deleteAttachFile(AttachFileVO af) throws Exception;
	
	

}
