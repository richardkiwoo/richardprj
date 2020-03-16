package kr.co.richardprj.dao;

import java.util.List;

import kr.co.richardprj.dto.FileVO;

public interface FileDAO {
	public List<FileVO> selectFile() throws Exception;
	
	public FileVO selectFileInfo(FileVO fileVo) throws Exception;

	public int insertFile(FileVO file) throws Exception;

	public List<FileVO> selectFileList() throws Exception;
	
	public int updateFileInfo(FileVO file) throws Exception;
	
	public int increaseDownCnt(FileVO file) throws Exception;
}
