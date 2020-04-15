
PRIMARY KEY (order_id), 
FOREIGN KEY (customer_sid) REFERENCES customer (sid)
);



CREATE TABLE member
(
code int  auto_increment COMMENT '회원코드' , 
mbrId varchar(50)  Not null  COMMENT '회원ID' , 
mbrPw varchar(60)  Not null  COMMENT '회원패스워드' , 
mbrEmail varchar(500)  Not null  COMMENT '회원이메일' , 
mbrName varchar(100)  Null  COMMENT '회원명' , 
mbrMobile varchar(15)  Null  COMMENT '회원휴대전화' , 
regDate date Null  COMMENT '등록일' , 
kakaoId varchar(50)  Null  COMMENT '카카오ID' , 
naverId varchar(50)  Null  COMMENT '네이버ID' , 
googleId varchar(50)  Null  COMMENT '구글ID' , 
nikname varchar(500)  Null  COMMENT '닉네임' , 
birthdate varchar(8)  Null  COMMENT '생년월일' , 
gender varchar(6)  Null  COMMENT '성별' , 
PRIMARY KEY (code)
);


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


-- 게시판(Board)
create table board (
boardId int  auto_increment COMMENT '게시판ID' , 
boardName VARCHAR(500)    COMMENT '게시판명' , 
boardType VARCHAR(10)    COMMENT '게시판유형' , 
seq int   COMMENT '정렬순서' , 
regDate DATE   COMMENT '생성일시' , 
useYn VARCHAR(1)    COMMENT '사용여부' , 
modDate DATE   COMMENT '수정일시' , 
PRIMARY KEY (boardId)
);

create table post (
boardId int   COMMENT '게시판ID' , 
postNo int  COMMENT '게시글번호' , 
parentPostNo int   COMMENT '상위게시글 번호' , 
postTitle VARCHAR(100)    COMMENT '게시글제목' , 
topPostYn VARCHAR(1)    COMMENT '공지여부' , 
dispYn VARCHAR(1)    COMMENT '전시여부' , 
replyYn VARCHAR(1)    COMMENT '댓글가능여부' , 
delYn VARCHAR(1)    COMMENT '삭제여부' , 
writer VARCHAR(30)    COMMENT '작성자ID' , 
regDate DATE   COMMENT '작성일' , 
modDate DATE   COMMENT '수정일' , 
readCnt int   COMMENT '조회수' , 
regIp VARCHAR(15)    COMMENT '등록IP' , 
PRIMARY KEY (boardId, postNo),
FOREIGN KEY (boardId) REFERENCES board (boardId)
);

create table contents (
boardId int   COMMENT '게시판ID' , 
postNo int   COMMENT '게시글번호' , 
contents TEXT   COMMENT '게시글내용' , 
PRIMARY KEY (boardId, postNo),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo)
);

-- 댓글 
create table reply(
boardId int   COMMENT '게시판ID' , 
postNo int   COMMENT '게시글번호' , 
replyNo int   COMMENT '댓글번호' , 
repCont VARCHAR(1000)    COMMENT '뎃글내용' , 
repWriter VARCHAR(30)    COMMENT '작성자ID' , 
modDate DATE   COMMENT '수정일' , 
regDate DATE   COMMENT '작성일' , 
delYn VARCHAR(1)    COMMENT '삭제여부' , 
PRIMARY KEY (boardId, postNo, replyNo),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo)
);

-- 추천
create table recommend (
likeNo int  auto_increment COMMENT '추천번호' , 
loginId VARCHAR(30)    COMMENT '로그인ID' , 
clCd VARCHAR(1)    COMMENT '구분코드' , 
boardId int   COMMENT '게시판번호' , 
postNo int   COMMENT '게시글번호' , 
replyNo int   COMMENT '댓글번호' , 
PRIMARY KEY (likeNo),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo),
FOREIGN KEY (boardId, postNo, replyNo) REFERENCES reply (boardId, postNo, replyNo)
);

create table attachfile (
boardId int   COMMENT '게시판ID' , 
postNo int   COMMENT '게시글번호' , 
fileSeq int   COMMENT '첨부파일ID' , 
fileName VARCHAR(300)    COMMENT '첨부파일명' , 
savedFileName VARCHAR(300)    COMMENT '파일저장명' , 
filePath VARCHAR(300)    COMMENT '첨부파일PATH' , 
fileSize bigint   COMMENT '파일크기' , 
fileType VARCHAR(10)    COMMENT '파일타입' , 
PRIMARY KEY (boardId, postNo, fileSeq),
FOREIGN KEY (boardId, postNo) REFERENCES post (boardId, postNo)
);