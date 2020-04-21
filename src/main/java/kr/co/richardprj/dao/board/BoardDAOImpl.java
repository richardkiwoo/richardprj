package kr.co.richardprj.dao.board;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.richardprj.dto.board.AttachFileVO;
import kr.co.richardprj.dto.board.BoardVO;
import kr.co.richardprj.dto.board.ContentsVO;
import kr.co.richardprj.dto.board.PostVO;
import kr.co.richardprj.dto.board.RecommendVO;
import kr.co.richardprj.dto.board.ReplyVO;

@Repository
public class BoardDAOImpl implements BoardDAO{
	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "kr.co.richardprj.mapper.boardMapper";

	@Override
	public List<BoardVO> selectBoardList(BoardVO board) throws Exception {
		return sqlSession.selectList(Namespace+".selectBoardList", board);
	}

	@Override
	public BoardVO selectBoard(BoardVO board) throws Exception {
		
		return null;
	}

	@Override
	public int insertBoard(BoardVO board) throws Exception {
		
		return sqlSession.insert(Namespace+".insertBoard", board);
	}

	@Override
	public int updateBoard(BoardVO board) throws Exception {
		return sqlSession.update(Namespace+".updteBoard", board);
	}

	@Override
	public int deleteBoard(BoardVO board) throws Exception {
		return sqlSession.delete(Namespace+".deleteBoard", board);
	}

	@Override
	public List<PostVO> selectPostList(PostVO post) throws Exception {
		
		return sqlSession.selectList(Namespace+".selectPostList", post);
	}
	
	@Override
	public int selectPostListCnt(PostVO post) throws Exception {
		return sqlSession.selectOne(Namespace+".selectPostListCnt", post);
	}

	@Override
	public PostVO selectPost(PostVO post) throws Exception {
		
		return (PostVO)sqlSession.selectOne(Namespace+".selectPost", post);
	}
	
	@Override
	public int selectMaxPostNo(PostVO post) throws Exception {
		
		return (Integer)sqlSession.selectOne(Namespace+".selectMaxPostNo", post);
	}

	@Override
	public int insertPost(PostVO post) throws Exception {
		
		return sqlSession.insert(Namespace+".insertPost", post);
	}

	@Override
	public int updatePost(PostVO post) throws Exception {
		
		return sqlSession.update(Namespace+".updatePost", post);
	}
	@Override
	public int increaseReadCnt(PostVO post) throws Exception {
		
		return sqlSession.update(Namespace+".increaseReadCnt", post);
	}

	@Override
	public int deletePost(PostVO post) throws Exception {
		
		return sqlSession.delete(Namespace+".deletePost", post);
	}
	
	@Override
	public ContentsVO selectContents(PostVO post) throws Exception {
		return (ContentsVO)sqlSession.selectOne(Namespace+".selectContents", post);
	}
	
	@Override
	public int insertContents(PostVO post) throws Exception {
		
		return sqlSession.insert(Namespace+".insertContents", post);
	}

	@Override
	public int updateContents(PostVO post) throws Exception {
		
		return sqlSession.update(Namespace+".updateContents", post);
	}

	@Override
	public int deleteContents(PostVO post) throws Exception {
		
		return sqlSession.delete(Namespace+".deleteContents", post);
	}

	@Override
	public List<ReplyVO> selectReplyList(PostVO post) throws Exception {
		
		return sqlSession.selectList(Namespace+".selectReplyList", post);
	}

	@Override
	public ReplyVO selectReply(ReplyVO post) throws Exception {
		
		return null;
	}
	
	@Override
	public int selectMaxReplyNo(ReplyVO rep) throws Exception {
		return (Integer)sqlSession.selectOne(Namespace+".selectMaxReplyNo", rep);
	}

	@Override
	public int insertReply(ReplyVO rep) throws Exception {
		
		return sqlSession.insert(Namespace+".insertReply", rep);
	}

	@Override
	public int updateReply(ReplyVO rep) throws Exception {
		
		return sqlSession.update(Namespace+".updateReply", rep);
	}
	
	@Override
	public int deleteReply(ReplyVO rep) throws Exception {
		
		return sqlSession.update(Namespace+".deleteReply", rep);
	}

	@Override
	public int selectRecommendCnt(PostVO post) throws Exception {
		return sqlSession.selectOne(Namespace+".selectRecommendCnt", post);
	}
	
	@Override
	public int insertRecommend(RecommendVO recommend) throws Exception {
		
		return sqlSession.insert(Namespace+".insertRecommend", recommend);
	}

	@Override
	public int deleteRecommend(RecommendVO recommend) throws Exception {
		
		return sqlSession.delete(Namespace+".deleteRecommend", recommend);
	}

	@Override
	public List<AttachFileVO> selectAttachFileList(PostVO post) throws Exception {
		
		return sqlSession.selectList(Namespace+".selectAttachFileList", post);
	}

	@Override
	public int insertAttachFile(AttachFileVO af) throws Exception {
		
		return sqlSession.insert(Namespace+".insertAttachFile", af);
	}

	@Override
	public int deleteAttachFile(AttachFileVO af) throws Exception {
		
		return sqlSession.delete(Namespace+".deleteAttachFile", af);
	}

	@Override
	public AttachFileVO selectAttachFile(AttachFileVO file) throws Exception {
		return sqlSession.selectOne(Namespace+".selectAttachFile", file);
	}



	
}
