


-- file upload 시 정보를 저장하는 테이블
CREATE TABLE FILE
(
  IDX   bigint      auto_increment     ,
  BOARDIDX bigint  NULL,
  ORIGINALFILENAME VARCHAR(260) NOT NULL,
  STOREDFILENAME VARCHAR(360) NOT NULL,
  FILESIZE double,
  CREATEDTM  DATE  NOT NULL,
  CREATORID   VARCHAR(30) NOT NULL,
  DOWNCNT  bigint NULL DEFAULT '0',
  DELYN    VARCHAR(1) DEFAULT 'N' NOT NULL,
  PRIMARY KEY (IDX)
);


create table game (
GameID Bigint  auto_increment COMMENT '게임번호' , 
SDCd VARCHAR(1)  Not null  COMMENT '단복식구분' , 
MbrId VARCHAR(30)  Not null  COMMENT '회원ID' , 
MbrNm VARCHAR(50)  Not null  COMMENT '회원성명' , 
TeamPlayerId VARCHAR(30)  Null  COMMENT '편회원ID' , 
TeamPlayerNm VARCHAR(40)  Not null  COMMENT '편회원성명' , 
OTeamPlayerId1 VARCHAR(30)  Null  COMMENT '상대편회원ID1' , 
OTeamPlayerId2 VARCHAR(30)  Null  COMMENT '상대편회원ID2' , 
OTeamPlayerNm1 VARCHAR(50)  Not null  COMMENT '상대편회원성명1' , 
OTeamPlayerNm2 VARCHAR(50)  Not null  COMMENT '상대편회원성명2' , 
WinLoseCd VARCHAR(1)  Not null  COMMENT '승패' , 
OurScore Int Not null  COMMENT '점수_자신' , 
OpponentScore Int Not null  COMMENT '점수_상대' , 
CourtNo VARCHAR(5)  Null  COMMENT '코트번호' , 
GameTime DATE Not null  COMMENT '경기시간' , 
UpdDate DATE Not null  COMMENT '수정시간' , 
PRIMARY KEY (GameId)
);