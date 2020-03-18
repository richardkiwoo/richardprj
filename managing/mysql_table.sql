


-- file upload �� ������ �����ϴ� ���̺�
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
GameID Bigint  auto_increment COMMENT '���ӹ�ȣ' , 
SDCd VARCHAR(1)  Not null  COMMENT '�ܺ��ı���' , 
MbrId VARCHAR(30)  Not null  COMMENT 'ȸ��ID' , 
MbrNm VARCHAR(50)  Not null  COMMENT 'ȸ������' , 
TeamPlayerId VARCHAR(30)  Null  COMMENT '��ȸ��ID' , 
TeamPlayerNm VARCHAR(40)  Not null  COMMENT '��ȸ������' , 
OTeamPlayerId1 VARCHAR(30)  Null  COMMENT '�����ȸ��ID1' , 
OTeamPlayerId2 VARCHAR(30)  Null  COMMENT '�����ȸ��ID2' , 
OTeamPlayerNm1 VARCHAR(50)  Not null  COMMENT '�����ȸ������1' , 
OTeamPlayerNm2 VARCHAR(50)  Not null  COMMENT '�����ȸ������2' , 
WinLoseCd VARCHAR(1)  Not null  COMMENT '����' , 
OurScore Int Not null  COMMENT '����_�ڽ�' , 
OpponentScore Int Not null  COMMENT '����_���' , 
CourtNo VARCHAR(5)  Null  COMMENT '��Ʈ��ȣ' , 
GameTime DATE Not null  COMMENT '���ð�' , 
UpdDate DATE Not null  COMMENT '�����ð�' , 
PRIMARY KEY (GameId)
);