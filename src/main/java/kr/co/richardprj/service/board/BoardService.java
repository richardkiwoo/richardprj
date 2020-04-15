package kr.co.richardprj.service.board;

import java.util.List;

import kr.co.richardprj.dto.board.PostVO;

public interface BoardService {
	public List<PostVO> getPostList(PostVO post) throws Exception;
	public PostVO getPost(PostVO post) throws Exception;
	public int insertPost(PostVO post) throws Exception;
	public int updatePost(PostVO post) throws Exception;
	
}
