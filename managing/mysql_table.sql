
PRIMARY KEY (order_id), 
FOREIGN KEY (customer_sid) REFERENCES customer (sid)
);



CREATE TABLE member
(
code int  auto_increment COMMENT 'ȸ���ڵ�' , 
mbrId varchar(50)  Not null  COMMENT 'ȸ��ID' , 
mbrPw varchar(60)  Not null  COMMENT 'ȸ���н�����' , 
mbrEmail varchar(500)  Not null  COMMENT 'ȸ���̸���' , 
mbrName varchar(100)  Null  COMMENT 'ȸ����' , 
mbrMobile varchar(15)  Null  COMMENT 'ȸ���޴���ȭ' , 
regDate date Null  COMMENT '�����' , 
kakaoId varchar(50)  Null  COMMENT 'īī��ID' , 
naverId varchar(50)  Null  COMMENT '���̹�ID' , 
googleId varchar(50)  Null  COMMENT '����ID' , 
nikname varchar(500)  Null  COMMENT '�г���' , 
birthdate varchar(8)  Null  COMMENT '�������' , 
gender varchar(6)  Null  COMMENT '����' , 
PRIMARY KEY (code)
);


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


-- �Խ���(Board)
create table board (
boardId int  auto_increment COMMENT '�Խ���ID' , 
boardName VARCHAR(500)    COMMENT '�Խ��Ǹ�' , 
boardType VARCHAR(10)    COMMENT '�Խ�������' , 
seq int   COMMENT '���ļ���' , 
regDate DATE   COMMENT '�����Ͻ�' , 
useYn VARCHAR(1)    COMMENT '��뿩��' , 
modDate DATE   COMMENT '�����Ͻ�' , 
PRIMARY KEY (boardId)
);

create table post (
boardId int   COMMENT '�Խ���ID' , 
postNo int  COMMENT '�Խñ۹�ȣ' , 
parentPostNo int   COMMENT '�����Խñ� ��ȣ' , 
postTitle VARCHAR(100)    COMMENT '�Խñ�����' , 
topPostYn VARCHAR(1)    COMMENT '��������' , 
dispYn VARCHAR(1)    COMMENT '���ÿ���' , 
replyYn VARCHAR(1)    COMMENT '��۰��ɿ���' , 
delYn VARCHAR(1)    COMMENT '��������' , 
writer VARCHAR(30)    COMMENT '�ۼ���ID' , 
regDate DATE   COMMENT '�ۼ���' , 
modDate DATE   COMMENT '������' , 
readCnt int   COMMENT '��ȸ��' , 
regIp VARCHAR(15)    COMMENT '���IP' , 
PRIMARY KEY (boardId, postNo),
FOREIGN KEY (boardId) REFERENCES board (boardId)
);

create table contents (
boardId int   COMMENT '�Խ���ID' , 
postNo int   COMMENT '�Խñ۹�ȣ' , 
contents TEXT   COMMENT '�Խñ۳���' , 
PRIMARY KEY (boardId, postNo),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo)
);

-- ��� 
create table reply(
boardId int   COMMENT '�Խ���ID' , 
postNo int   COMMENT '�Խñ۹�ȣ' , 
replyNo int   COMMENT '��۹�ȣ' , 
repCont VARCHAR(1000)    COMMENT '���۳���' , 
repWriter VARCHAR(30)    COMMENT '�ۼ���ID' , 
modDate DATE   COMMENT '������' , 
regDate DATE   COMMENT '�ۼ���' , 
delYn VARCHAR(1)    COMMENT '��������' , 
PRIMARY KEY (boardId, postNo, replyNo),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo)
);

-- ��õ
create table recommend (
likeNo int  auto_increment COMMENT '��õ��ȣ' , 
loginId VARCHAR(30)    COMMENT '�α���ID' , 
clCd VARCHAR(1)    COMMENT '�����ڵ�' , 
boardId int   COMMENT '�Խ��ǹ�ȣ' , 
postNo int   COMMENT '�Խñ۹�ȣ' , 
replyNo int   COMMENT '��۹�ȣ' , 
PRIMARY KEY (likeNo),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo),
FOREIGN KEY (boardId, postNo, replyNo) REFERENCES reply (boardId, postNo, replyNo)
);

create table attachfile (
boardId int   COMMENT '�Խ���ID' , 
postNo int   COMMENT '�Խñ۹�ȣ' , 
fileSeq int   COMMENT '÷������ID' , 
fileName VARCHAR(300)    COMMENT '÷�����ϸ�' , 
savedFileName VARCHAR(300)    COMMENT '���������' , 
filePath VARCHAR(300)    COMMENT '÷������PATH' , 
fileSize bigint   COMMENT '����ũ��' , 
fileType VARCHAR(10)    COMMENT '����Ÿ��' , 
PRIMARY KEY (boardId, postNo, fileSeq),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo)
);