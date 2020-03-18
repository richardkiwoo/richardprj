package kr.co.richardprj.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.controller.HomeController;

import kr.co.richardprj.dao.FileDAO;
import kr.co.richardprj.dto.FileVO;

@Service
public class FileServiceImpl implements FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Inject
	private FileDAO dao;
	
	@Override
	public List<FileVO> getFileList() throws Exception {
		return dao.selectFileList();
	}

	@Override
	public FileVO getFile(String filename) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateFileInfo(String filename) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delteFile(FileVO file) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertFileInfo(FileVO fileVO) throws Exception {
		return dao.insertFile(fileVO);
	}

	@Override
	public FileVO getFileInfo(FileVO fileVO) throws Exception {
		return dao.selectFileInfo(fileVO);
	}

	@Override
	public int increaseDownCnt(FileVO file) throws Exception {
		logger.info("increaseDownCnt called.......");
		return dao.increaseDownCnt(file);
	}

	

}
