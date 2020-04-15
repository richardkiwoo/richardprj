package kr.co.richardprj.dto.board;

import lombok.Data;

@Data
public class PostVO {
	private int    boardId     ;
	private int    postNo      ;
	private int    parentPostNo;
	private String postTitle   ;
	private String topPostYn   ;
	private String dispYn      ;
	private String replyYn     ;
	private String delYn       ;
	private String writer      ;
	private String regDate     ;
	private String modDate     ;
	private int    readCnt     ;
	private String regIp       ;
	private String contents;
	private int  recommendCnt;
}
