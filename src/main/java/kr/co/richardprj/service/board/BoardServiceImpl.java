package kr.co.richardprj.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.dao.MemberDAO;

import kr.co.richardprj.dao.board.BoardDAO;
import kr.co.richardprj.dto.board.PaginationVO;
import kr.co.richardprj.dto.board.PostVO;

@Service
public class BoardServiceImpl implements BoardService{

	@Inject
	private BoardDAO boardDao; 
	
	@Inject
	private MemberDAO memberDao; 
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertPost(PostVO post) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePost(PostVO post) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
