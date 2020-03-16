package kr.co.richardprj.service;

import java.util.List;

import kr.co.richardprj.dto.FileVO;

public interface FileService {
	public List<FileVO> getFileList() throws Exception ;
	
	public FileVO getFile(String filename) throws Exception;
	
	public FileVO getFileInfo(FileVO fileVO) throws Exception;
	
	public int insertFileInfo(FileVO fileVO) throws Exception;
	
	public int updateFileInfo(String filename) throws Exception;
	
	public int delteFile(FileVO file) throws Exception;
	
	public int increaseDownCnt(FileVO file) throws Exception;
}	
