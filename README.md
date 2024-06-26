### 과제 1) 업무 분담

1. 회원

- 프론트 (서석준) / 백 (이소연, 류상경)
    - 회원가입 기능
        - 이메일, 비밀번호, 비밀번호 확인, 프로필사진진, 핸드폰 번호, 주민등록번호, 상세 주소 등
        - 이메일, 닉네임(아이디) 중복 체크 - 유니크 제약조건
        - 비밀번호와 비밀번호 확인 칸이 일치하면 회원 가입성공 -> 불일치 하면 "비밀번호가 다릅니다" 문구 생성
        - 핸드폰 번호 입력시 인증 화면 창과 핸드폰 번호로 인증하도록 설계 (가능하면)
        - 회원 가입 관련된 항목 중 빈칸이 있는 경우 -> "빈칸을 입력해주세요" 라는 문구 생성
    - 로그인/로그아웃
        - 시큐리티 적용
        - 로그인시 아이디 값과 비밀번호가 값이 일치 하지 않으면 로그인 실패 -> 일치하는 경우 로그인 성공
            - 아이디, 비밀번호가 없거나 다른경우 : "아이디 혹은 비밀번호 오류입니다.." 문구 생성
        - 로그아웃 버튼
        - 로그인 후 연장하기 버튼 설정
    - 회원 정보 수정
    - 아이디, 비밀번호 찾기
        - 아이디를 모를 때, 이름과 이메일을 입력하면 인증 후 새로운 창을 띄워 아이디를 알려줌.
        - 이메일주소와 이름을 입력시 임시비밀번호를 이메일로 발송
    - 회원탈퇴
        - 회원탈퇴시 탈퇴한 사람이 작성한 글을 같이 삭제하도록 설계
        - 회원탈퇴 버튼을 누를 시 "정말 탈퇴하시겠습니까?" 문구 생성 비밀번호 입력 후 일치할 시 탈퇴 가능

2. 게시판

- 프론트 (양지성) / 백 (박성준, 한재연)
    - 게시글 작성(QNA, 공지사항, 관리자 회원조회)
        - 제목, 내용, 사진, 동영상, 파일 첨부
        - QNA 게시판에는 로그인한 회원들만 게시글을 작성할 수 있도록 설계
        - 공지사항은 관리자계정만 작성할 수 있도록 설계
        - 게시글 작성 완료시 "게시글이 등록되었습니다." 문구가 나오도록 설계
    - 게시글 삭제
        - 공지사항은 관리자계정만 삭제할 수 있도록 설계
        - QNA는 본인 게시글만 삭제할 수 있도록 설계
        - 게시글 삭제시 사진, 동영상 등 첨부 파일이 같이 삭제 되도록 설계
        - 게시글 삭제 버튼을 누를 시 "이 게시물을 삭제하시겠습니까?" 문구가 나오도록 설계
        - 게시글 삭제시 "삭제가 완료되었습니다." 문구가 나오도록 설계
    - 게시글 수정
        - 작성자만 게시글을 수정 할 수 있음
        - 제목, 내용, 사진, 동영상, 파일 수정
        - 게시글 수정 버튼을 누를 시 "수정하시겠습니까?" 문구가 나오도록 설계
        - 게시글 수정 완료시 "수정이 완료되었습니다." 문구가 나오도록 설계
    - 게시글 리스트 보기
        - 최신 게시물이 리스트 형식으로 출력
        - 작성자의 프로필 사진, 닉네임, 제목, 내용, 댓글 수, 조회수가 보여짐
        - 페이징 처리 5개씩 10페이지
    - 게시글 상세보기
        - 조회 수와 댓글 수 표시
        - 댓글 작성
        - 답글 작성
        - 내용만 작성
        - 비밀글, 공개글
    - 페이징 처리
        - 한페이지에 5개씩 최대 10페이지까지
        - 이전 섹션, 다음 섹션 기능
    - 검색 기능

#### User - Board 관계

1. User가 Board를 작성함. 1:N
2. User가 Board에 좋아요. N:M
3. User가 Board에 댓글담. N:M

### 개발 환경

- SpringBoot, OracleDB, React, JPA, MY BATIS

#### NPM START시 API 오류 발생하는 경우

1. reactfront 패키지에 .env 파일 생성
2. DANGEROUSLY_DISABLE_HOST_CHECK=true 입력
3. cd src/main/reactfront -> npm start

#### @mui 관련 오류 발생시

- npm add @mui/icons-material

#### axios install

npm install -save axios

### 오라클 데이터베이스 계정 생성

1. 계정 생성

- CREATE USER team4 IDENTIFIED BY 1234;

2. 권한 부여

- GRANT CONNECT, RESOURCE TO team4;

3. 시퀀스 조회

- -- 시퀀스명으로 조회(세부정보)
  SELECT * FROM all_sequences
  WHERE sequence_name = 'BOARD_SEQ'

- -- 유저명으로 조회(리스트)
  SELECT * FROM all_sequences
  WHERE sequence_owner = 'TEAM4'

    1. 테이블 삭제
       drop table likes;
       drop table dcard;
       drop table taxrefund;
       drop table files;
       drop table board;
       drop table reply;
       drop table qna;
       drop table members;
    2. 시퀀스 삭제
       drop sequence BOARD_SEQ;
       drop sequence member_seq;
       drop sequence qna_seq;
       drop sequence REPLY_SEQ;

#### 2023. 05. 02 전달사항 및 필요사항

1. 프로젝트 개요 정리해서 멘토님한테 전달
2. 질문사항 모아서 멘토님한테 물어보기
3. react 폴더 정리 및 이름 변경

#### QNA 관련 오라클 명령어

INSERT INTO QNA (QNO, TITLE, content, regdate) VALUES (QNA_SEQ.NEXTVAL, '테스트2', '테스트2', TO_DATE('12:30:30', 'HH24:MI:
SS'));

#### 2023.05.25 전달사항

1. Redis를 통하여 토큰을 관리하기 때문에 깔아줄 것.
2. https://github.com/microsoftarchive/redis/releases

## 2023.06.19 더미데이터 생성

## sqldeveloper로 넣기

