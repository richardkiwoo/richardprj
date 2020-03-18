package kr.co.richardprj.service;

import java.util.List;

import kr.co.richardprj.dto.GameVO;

public interface GameService {
	
	public List<GameVO> getGameList() throws Exception ;
	
	public GameVO getGame(int gameNo) throws Exception;
	
	public int insertGameResult(GameVO gameVO) throws Exception;
	
	public int updateGameResult(GameVO gameVO) throws Exception;
	
	public int deleteGame(GameVO game, int gameNo) throws Exception;

	
}
