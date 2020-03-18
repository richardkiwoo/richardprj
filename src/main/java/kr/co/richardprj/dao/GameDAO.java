package kr.co.richardprj.dao;

import java.util.List;

import kr.co.richardprj.dto.GameVO;

public interface GameDAO {
	public List<GameVO> selectGameList() throws Exception;
	
	public GameVO selectGameInfo(GameVO gameVo) throws Exception;

	public int insertGameResult(GameVO game) throws Exception;

	public int updateGameResult(GameVO game) throws Exception;
}
