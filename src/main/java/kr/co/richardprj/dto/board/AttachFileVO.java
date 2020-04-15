package kr.co.richardprj.dto.board;

import lombok.Data;

@Data
public class AttachFileVO {
	private int boardId       ;    
	private int boardNo       ;
	private int fileSeq       ;
	private String fileName      ;
	private String savedFileName ;
	private String filePath      ;
	private float fileSize      ;
	private String fileType      ;
}
