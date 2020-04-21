package kr.co.richardprj.dto;

import lombok.Data;

@Data
public class FileVO {
	 private String idx                ;
	 private int    boardId           ;
	 private int    postNo;
	 private String originalFileName   ;
	 private String storedFileName     ;
	 private long  fileSize           ;
	 private String createDtm          ;
	 private String creatorId          ;
	 private String delYn              ;
	 private int    downCnt ;
	 
}
