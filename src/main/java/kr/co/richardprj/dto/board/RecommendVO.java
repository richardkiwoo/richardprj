package kr.co.richardprj.dto.board;

import lombok.Data;

@Data
public class RecommendVO {
	private int    likeNo ;
	private String loginId;
	private String clCd   ;
	private int    boardId;
	private int    postNo ;
	private int    replyNo;
}
