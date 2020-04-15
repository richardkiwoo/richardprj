package kr.co.richardprj.dto.board;

import lombok.Data;

@Data
public class ReplyVO {

	private int boardId    ;
	private int boardNo    ;
	private int repNo      ;
	private int parentRepNo;
	private String repCont    ;
	private String repWriter  ;
	private String modDate    ;
	private String regDate    ;
	private int  recommendCnt;
	
}
