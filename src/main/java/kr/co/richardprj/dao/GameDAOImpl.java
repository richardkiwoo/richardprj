package kr.co.richardprj.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.richardprj.dto.GameVO;

@Repository
public class GameDAOImpl implements GameDAO{

	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "kr.co.richardprj.mapper.gameMapper";
	
	@Override
	public List<GameVO> selectGameList() throws Exception {
		return sqlSession.selectList(Namespace+".selectGameList", new GameVO());
	}

	@Override
	public GameVO selectGameInfo(GameVO gameVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertGameResult(GameVO game) throws Exception {
		return sqlSession.update(Namespace+".insertGameResult", game);
	}

	@Override
	public int updateGameResult(GameVO game) throws Exception {
		return sqlSession.update(Namespace+".updateGameResult", game);
	}

}
