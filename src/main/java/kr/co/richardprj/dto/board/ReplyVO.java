package kr.co.richardprj.dto.board;

import lombok.Data;

@Data
public class ReplyVO {

	private int boardId    ;
	private int postNo    ;
	private int replyNo      ;
	private String repCont    ;
	private String repWriter  ;
	private String modDate    ;
	private String regDate    ;
	private String delYn    ;
	private int  recommendCnt;
	
}

