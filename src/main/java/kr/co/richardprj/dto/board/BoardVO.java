package kr.co.richardprj.dto.board;

import lombok.Data;

@Data
public class BoardVO {
	private int    boardId     ;
	private String boardName   ;
	private String boardType   ;
	private int    seq         ;
	private String regDate     ;
	private String useYn       ;
	private String modDate     ;
}
