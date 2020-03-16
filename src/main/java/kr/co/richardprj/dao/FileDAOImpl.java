package kr.co.richardprj.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.richardprj.dto.FileVO;

@Repository
public class FileDAOImpl implements FileDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "kr.co.richardprj.mapper.fileMapper";

	@Override
	public List<FileVO> selectFile() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertFile(FileVO file) throws Exception {
		int result = sqlSession.insert(Namespace+".insertFileInfo",file);
		return result;
	}

	@Override
	public List<FileVO> selectFileList() throws Exception {
		return sqlSession.selectList(Namespace+".selectFileInfo", null);
	}

	@Override
	public int updateFileInfo(FileVO file) throws Exception {
		return 0;
	}

	@Override
	public FileVO selectFileInfo(FileVO fileVo) throws Exception {
		return sqlSession.selectOne(Namespace+".selectFileInfo", fileVo);
	}

	@Override
	public int increaseDownCnt(FileVO file) throws Exception {
		return sqlSession.update(Namespace+".increaseDownCnt", file);
	}

	
}
