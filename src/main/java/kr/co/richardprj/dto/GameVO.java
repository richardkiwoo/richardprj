package kr.co.richardprj.dto;

public class GameVO {
	private int    gameID ;
	private String sDCd ;
	private String mbrId ;
	private String mbrNm ;
	private String teamPlayerId ;
	private String teamPlayerNm ;
	private String oTeamPlayerId1 ;
	private String oTeamPlayerId2 ;
	private String oTeamPlayerNm1 ;
	private String oTeamPlayerNm2 ;
	private String winLoseCd ;
	private int    ourScore ;
	private int    opponentScore ;
	private String courtNo ;
	private String gameTime ;
	private String updDate ;
	
	
	
	
	
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public String getsDCd() {
		return sDCd;
	}
	public void setsDCd(String sDCd) {
		this.sDCd = sDCd;
	}
	public String getMbrId() {
		return mbrId;
	}
	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}
	public String getMbrNm() {
		return mbrNm;
	}
	public void setMbrNm(String mbrNm) {
		this.mbrNm = mbrNm;
	}
	public String getTeamPlayerId() {
		return teamPlayerId;
	}
	public void setTeamPlayerId(String teamPlayerId) {
		this.teamPlayerId = teamPlayerId;
	}
	public String getTeamPlayerNm() {
		return teamPlayerNm;
	}
	public void setTeamPlayerNm(String teamPlayerNm) {
		this.teamPlayerNm = teamPlayerNm;
	}
	public String getoTeamPlayerId1() {
		return oTeamPlayerId1;
	}
	public void setoTeamPlayerId1(String oTeamPlayerId1) {
		this.oTeamPlayerId1 = oTeamPlayerId1;
	}
	public String getoTeamPlayerId2() {
		return oTeamPlayerId2;
	}
	public void setoTeamPlayerId2(String oTeamPlayerId2) {
		this.oTeamPlayerId2 = oTeamPlayerId2;
	}
	public String getoTeamPlayerNm1() {
		return oTeamPlayerNm1;
	}
	public void setoTeamPlayerNm1(String oTeamPlayerNm1) {
		this.oTeamPlayerNm1 = oTeamPlayerNm1;
	}
	public String getoTeamPlayerNm2() {
		return oTeamPlayerNm2;
	}
	public void setoTeamPlayerNm2(String oTeamPlayerNm2) {
		this.oTeamPlayerNm2 = oTeamPlayerNm2;
	}
	public String getWinLoseCd() {
		return winLoseCd;
	}
	public void setWinLoseCd(String winLoseCd) {
		this.winLoseCd = winLoseCd;
	}
	public int getOurScore() {
		return ourScore;
	}
	public void setOurScore(int ourScore) {
		this.ourScore = ourScore;
	}
	public int getOpponentScore() {
		return opponentScore;
	}
	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}
	public String getCourtNo() {
		return courtNo;
	}
	public void setCourtNo(String courtNo) {
		this.courtNo = courtNo;
	}
	public String getGameTime() {
		return gameTime;
	}
	public void setGameTime(String gameTime) {
		this.gameTime = gameTime;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	@Override
	public String toString() {
		return "GameVO [gameID=" + gameID + ", sDCd=" + sDCd + ", mbrId=" + mbrId + ", mbrNm=" + mbrNm + ", winLoseCd="
				+ winLoseCd + ", ourScore=" + ourScore + ", opponentScore=" + opponentScore + "]";
	}
	
	
}
