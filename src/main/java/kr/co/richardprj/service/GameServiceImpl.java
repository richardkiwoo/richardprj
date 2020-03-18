package kr.co.richardprj.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.richardprj.dao.GameDAO;
import kr.co.richardprj.dto.GameVO;

@Service
public class GameServiceImpl implements GameService{
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Inject
	private GameDAO dao;
	
	@Override
	public List<GameVO> getGameList() throws Exception {
		return dao.selectGameList();
	}

	@Override
	public GameVO getGame(int gameNo) throws Exception {
		GameVO  gameVO = new GameVO();
		gameVO.setGameID(gameNo);
		
		return dao.selectGameInfo(gameVO);
	}

	@Override
	public int insertGameResult(GameVO gameVO) throws Exception {
		logger.info("insertGame...");
		return dao.insertGameResult(gameVO);
	}

	@Override
	public int updateGameResult(GameVO gameVO) throws Exception {
		return dao.updateGameResult(gameVO);
	}

	@Override
	public int deleteGame(GameVO game, int gameNo) throws Exception {
		return 0;
	}

}
