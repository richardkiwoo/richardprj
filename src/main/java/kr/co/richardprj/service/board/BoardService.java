package kr.co.richardprj.service.board;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.richardprj.dto.board.PostVO;

public interface BoardService {
	public List<PostVO> getPostList(PostVO post) throws Exception;
	public int selectPostListCnt(PostVO post) throws Exception;
	public PostVO getPost(PostVO post) throws Exception;
	public PostVO getPostOnly(PostVO post) throws Exception;
	public int insertPost(PostVO post) throws Exception;
	public int insertPost(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception;
	public int increaseReadCnt(PostVO post) throws Exception;
	public int updatePost(PostVO post) throws Exception;
	public int updatePost(PostVO post, MultipartHttpServletRequest mpRequest) throws Exception;
	public int deletePost(PostVO post) throws Exception;
	
}
